package uci.mswe.swe242p.ex4_udp_file_server;

import uci.mswe.swe242p.ex3_tcp_file_server.*;
import static uci.mswe.swe242p.ex3_tcp_file_server.Logger.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class UDPFileServer {

  private static final int PORT = 8000;
  final static int READ_TIMEOUT = 1000;

  private static final int IN_BUFFER_SIZE = 1024; // That is the longest size of a command
  private static final int OUT_BUFFER_SIZE = 8192;
  // see page 402-403 on Java Network Programming
  /**
   * https://stackoverflow.com/questions/3396813/message-too-long-for-udp-socket-after-setting-sendbuffersize
   *
   * The limit on a UDP datagram payload in IPv4 is 65535-28=65507 bytes, and the practical limit is
   * the MTU of the path which is more like 1460 bytes if you're lucky.
   */

  private static String folderPathString = "";
  private static Path folderPath = null;

  private static DatagramSocket serverSocket = null;

  private static class ResponseTask implements Callable<Void> {

    private DatagramPacket packetFromClient;
    private String clientAddressAndPort;
    private StringWriter responseWriter;
    private DatagramPacket packetToClient;

    ResponseTask(DatagramPacket packet) {
      this.packetFromClient = packet;

      final var address = packet.getAddress();
      final var port = packet.getPort();
      this.clientAddressAndPort = address.getHostAddress() + ":" + port;

      this.responseWriter = new StringWriter();

      this.packetToClient = new DatagramPacket(new byte[0], 0, address, port);
    }


    public void sendResponseString() throws IOException {
      // TODO: Make it reliable like TCP, by adding ACK and SEQ
      // Solution 1: stop-and-wait protocol (easier)
      // Solution 2: sliding window protocol (complex)
      // https://www.geeksforgeeks.org/stop-and-wait-arq/

      // We do not need EOR here
      // get the data ready
      var bytes = responseWriter.toString().getBytes();
      packetToClient.setData(bytes, 0, bytes.length);

      sendPacketReliably();
    }

    public void sendResponseString(String s) throws IOException {
      var bytes = s.getBytes();
      packetToClient.setData(bytes, 0, bytes.length);

      sendPacketReliably();
    }

    public void sendPacketReliably() throws IOException {
      // send the packet
      serverSocket.send(packetToClient);
      logit("response packet sent to " + clientAddressAndPort);

      // get ready for receiving ACK from client
      var inBytes = new byte[IN_BUFFER_SIZE];
      packetFromClient.setData(inBytes, 0, IN_BUFFER_SIZE);

      // and wait for ACK from client
      final var MAX_RETRY = 3;
      var currentTry = 0;
      serverSocket.setSoTimeout(READ_TIMEOUT); // wait for up to 3 sec
      while (currentTry < MAX_RETRY) {
        try {
          logit("Try count: " + currentTry + "/" + MAX_RETRY);
          serverSocket.receive(packetFromClient);
          // Blocking: Timeout OR Proceed
          var msgFromClient = new String(inBytes).replace("\0", "");
          logit("received message from client: " + msgFromClient);
          if (msgFromClient.equals("ACK")) {
            break;
          } else {
            continue; // ? is it possible that client sends other things ?
          }
        } catch (SocketTimeoutException e) {
          loget("ACK timeout.");

          serverSocket.send(packetToClient);
          logit("response packet sent to " + clientAddressAndPort);
        }
        ++currentTry;
      }

      serverSocket.setSoTimeout(0);
      if (currentTry == MAX_RETRY) {
        loget("NO response from client. This can be due to\n"
            + "1. Packet failed to arrive at the client;\n"
            + "2. Packet delivered to the client but the ACK was lost.");
      } else {
        logit("Client successfully received the packet.");
      }
    }

    @Override
    public Void call() throws Exception {
      logit("=== ResponseTask.call() starts ===");
      var toResString = new PrintWriter(new BufferedWriter(responseWriter), true);
      try {
        // data size = IN_BUFFER_SIZE
        var command = new String(packetFromClient.getData()).replace("\0", "");
        logit("received command \"" + command + "\" from " + clientAddressAndPort);
        switch (command) {
          case "index": {
            try {
              toResString.println("Index of " + folderPath.toAbsolutePath().toString());
              Files.walk(folderPath, 1) // depth = 1
                  .filter(Files::isRegularFile) // ignore folders
                  .map(Path::getFileName) //
                  .map(Path::toString) //
                  .collect(Collectors.toList()) //
                  .forEach(toResString::println);
              sendResponseString();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
            break;

          default: {
            if (!command.startsWith("get ")) {
              loget("unknown command received.");
            } else {
              var fileName = command.split(" ")[1];
              var filePath = Path.of(folderPathString + fileName);

              if (Files.notExists(filePath)) {

                loget(filePath.toString() + " does not exist.");

                toResString.println(Messages.ERROR.toString());
                toResString.println("File not found.");
                sendResponseString();

              } else {
                logit("processing " + filePath);

                toResString.println(Messages.OK.toString());
                Files.lines(filePath).forEach(toResString::println);
                // Now we have a string that has all content of the file
                // But the size of the string may exceed UDP limit
                // So we have to split the string into packets
                var data = responseWriter.toString();
                var len = data.length();
                if (len <= OUT_BUFFER_SIZE) {

                  logit("len(" + len + ") <= OUT_BUFFER_SIZE(" + OUT_BUFFER_SIZE + //
                      "), sending " + filePath + " directly");
                  sendResponseString();

                } else {

                  logit("len(" + len + ") <= OUT_BUFFER_SIZE(" + OUT_BUFFER_SIZE + //
                      "), splitting " + filePath);

                  // Split the string here
                  var splitCounts = len / OUT_BUFFER_SIZE;
                  var beginIndex = 0;
                  var endIndex = OUT_BUFFER_SIZE;

                  for (var i = 0; i < splitCounts; ++i) {
                    logit("sending " + filePath + ": " + i + "/" + splitCounts);
                    sendResponseString(data.substring(beginIndex, endIndex));
                    beginIndex += OUT_BUFFER_SIZE;
                    endIndex += OUT_BUFFER_SIZE;
                  }

                  if (beginIndex < len) {
                    logit("sending " + filePath + ": " + splitCounts + "/" + splitCounts);
                    sendResponseString(data.substring(beginIndex, len));
                  }
                }
              }
            }
          }
            break;
        }
      } catch (Exception e) {
        loget("an exception occured when running ResponseTask.");
        e.printStackTrace();
      }
      logit("=== ResponseTask.call() ends ===");

      return null;
    }

  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Please input ONE folder path.");
      return;
    }

    // make sure there is a '/' at the end of the path
    // and replace all "./" or ".\"
    folderPathString = (args[0] + '/')//
        .replaceAll("\\\\", "/")//
        .replaceAll("[/]+", "/")//
        .replaceAll("\\./", "");
    folderPath = Path.of(folderPathString);

    if (!Files.isDirectory(folderPath)) {
      System.err.println("Path is not valid.");
      return;
    }

    logit("server monitoring folder " + folderPathString);

    var pool = Executors.newFixedThreadPool(2);
    try {
      // UDP server socket has to be exposed to threads
      serverSocket = new DatagramSocket(PORT);
      logit("server listening on port " + PORT);

      while (true) {
        try {
          // Though client sends a few bytes, the packet here is still of size 1024
          var clientPacket = new DatagramPacket(new byte[IN_BUFFER_SIZE], IN_BUFFER_SIZE);
          serverSocket.receive(clientPacket); // blocking IO

          logit("packet received from " + //
              clientPacket.getAddress().getHostAddress() + ":" + clientPacket.getPort());

          pool.submit(new ResponseTask(clientPacket));

          Thread.sleep(2000); // let the sub-thread receive ACK
        } catch (Exception e) {
          loget("failed to accept a connection.");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      loget("failed to start a server.");
      e.printStackTrace();
    } finally {
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
