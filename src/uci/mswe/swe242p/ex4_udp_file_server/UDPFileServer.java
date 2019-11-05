package uci.mswe.swe242p.ex4_udp_file_server;

import uci.mswe.swe242p.ex3_tcp_file_server.*;
import static uci.mswe.swe242p.ex3_tcp_file_server.Logger.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class UDPFileServer {

  private static final int PORT = 8000;
  private static final int IN_BUFFER_SIZE = 1024;
  // private static final int OUT_BUFFER_SIZE = 8192; // see page 402-403 on Java

  private static String folderPathString = "";
  private static Path folderPath = null;

  private static DatagramSocket serverSocket = null;

  private static class ResponseTask implements Callable<Void> {

    private DatagramPacket clientPacket;
    private String clientAddressAndPort;


    ResponseTask(DatagramPacket packet) {
      this.clientPacket = packet;
      this.clientAddressAndPort =
          clientPacket.getAddress().getHostAddress() + ":" + clientPacket.getPort();
    }

    public void sendResponseString(StringWriter str) throws IOException {
      var bytes = str.toString().getBytes();
      serverSocket.send(new DatagramPacket(bytes, bytes.length, //
          clientPacket.getAddress(), clientPacket.getPort()));
      logi("response packet sent to " + clientAddressAndPort);
    }


    @Override
    public Void call() throws Exception {
      try {
        // data size = IN_BUFFER_SIZE
        var command = new String(clientPacket.getData()).replace("\0", "");
        logi("received command \"" + command + "\" from " + //
            clientPacket.getAddress() + ":" + clientPacket.getPort());

        var resString = new StringWriter();
        var toResString = new PrintWriter(new BufferedWriter(resString), true);
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

              sendResponseString(resString);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
            break;

          default: {
            if (!command.startsWith("get ")) {
              loge("unknown command received.");
            } else {
              var fileName = command.split(" ")[1];
              var filePath = Path.of(folderPathString + fileName);
              if (Files.notExists(filePath)) {
                toResString.println(Messages.ERROR.toString());
                toResString.println("File not found.");
                sendResponseString(resString);
              } else {
                toResString.println(Messages.OK.toString());
                // TODO: large file splitting
                Files.lines(filePath).forEach(toResString::println); // read file and send to client
                sendResponseString(resString);
              }
            }
          }
            break;
        }
      } catch (Exception e) {
        loge("an exception occured when running ResponseTask.");
        e.printStackTrace();
      }
      return null;
    }
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Please input ONE folder path.");
      return;
    }

    folderPathString = args[0];
    folderPath = Path.of(folderPathString);
    /**
     * API Note:
     *
     * It is recommended to obtain a Path via the Path.of methods instead of via the get methods
     * defined in this class as this class may be deprecated in a future release.
     */

    if (!Files.isDirectory(folderPath)) {
      System.err.println("Path is not valid.");
      return;
    }

    logi("server monitoring folder " + folderPathString);

    var pool = Executors.newFixedThreadPool(50);
    try {
      // server socket has to be exposed to threads
      serverSocket = new DatagramSocket(PORT);
      logi("server listening on port " + PORT);

      while (true) {
        try {
          // Though client sends a few bytes, the packet here is still of size 1024
          var clientPacket = new DatagramPacket(new byte[IN_BUFFER_SIZE], IN_BUFFER_SIZE);
          serverSocket.receive(clientPacket); // blocking IO

          logi("packet received from " + //
              clientPacket.getAddress().getHostAddress() + ":" + clientPacket.getPort());

          pool.submit(new ResponseTask(clientPacket));
        } catch (Exception e) {
          loge("failed to accept a connection.");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      loge("failed to start a server.");
      e.printStackTrace();
    } finally {
      try {
        serverSocket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
