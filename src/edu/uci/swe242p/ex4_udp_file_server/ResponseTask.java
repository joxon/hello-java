package edu.uci.swe242p.ex4_udp_file_server;

import edu.uci.swe242p.ex3_tcp_file_server.Messages;

import static edu.uci.swe242p.ex3_tcp_file_server.Logger.loget;
import static edu.uci.swe242p.ex3_tcp_file_server.Logger.logit;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

class ResponseTask implements Callable<Void> {

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
    UDPFileServer.serverSocket.send(packetToClient);
    logit("response packet sent to " + clientAddressAndPort);

    // get ready for receiving ACK from client
    var inBytes = new byte[UDPFileServer.IN_BUFFER_SIZE];
    packetFromClient.setData(inBytes, 0, UDPFileServer.IN_BUFFER_SIZE);

    // and wait for ACK from client
    final var MAX_RETRY = 3;
    var currentTry = 0;
    UDPFileServer.serverSocket.setSoTimeout(UDPFileServer.READ_TIMEOUT); // wait for up to 3 sec
    while (currentTry < MAX_RETRY) {
      try {
        logit("Try count: " + currentTry + "/" + MAX_RETRY);
        UDPFileServer.serverSocket.receive(packetFromClient);
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

        UDPFileServer.serverSocket.send(packetToClient);
        logit("response packet sent to " + clientAddressAndPort);
      }
      ++currentTry;
    }

    UDPFileServer.serverSocket.setSoTimeout(0);
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
            toResString.println("Index of " + UDPFileServer.folderPath.toAbsolutePath().toString());
            Files.walk(UDPFileServer.folderPath, 1) // depth = 1
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
            var filePath = Path.of(UDPFileServer.folderPathString + fileName);

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
              if (len <= UDPFileServer.OUT_BUFFER_SIZE) {

                logit("len(" + len + ") <= OUT_BUFFER_SIZE(" + UDPFileServer.OUT_BUFFER_SIZE + //
                    "), sending " + filePath + " directly");
                sendResponseString();

              } else {

                logit("len(" + len + ") <= OUT_BUFFER_SIZE(" + UDPFileServer.OUT_BUFFER_SIZE + //
                    "), splitting " + filePath);

                // Split the string here
                var splitCounts = len / UDPFileServer.OUT_BUFFER_SIZE;
                var beginIndex = 0;
                var endIndex = UDPFileServer.OUT_BUFFER_SIZE;

                for (var i = 0; i < splitCounts; ++i) {
                  logit("sending " + filePath + ": " + i + "/" + splitCounts);
                  sendResponseString(data.substring(beginIndex, endIndex));
                  beginIndex += UDPFileServer.OUT_BUFFER_SIZE;
                  endIndex += UDPFileServer.OUT_BUFFER_SIZE;
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
