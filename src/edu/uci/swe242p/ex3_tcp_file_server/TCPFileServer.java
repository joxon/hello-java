package edu.uci.swe242p.ex3_tcp_file_server;

import static edu.uci.swe242p.ex3_tcp_file_server.Logger.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class TCPFileServer {

  private static final int PORT = 8000;

  public static String folderPathString = "";
  public static Path folderPath = null;

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
    try (var serverSocket = new ServerSocket(PORT)) {
      // TCP server socket do NOT need to be exposed to threads
      logi("server listening on port " + PORT);

      while (true) {
        try {
          var clientSocket = serverSocket.accept(); // blocking IO

          logi("socket connected from " + //
              clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

          pool.submit(new ResponseTask(clientSocket));
        } catch (Exception e) {
          loge("failed to accept a connection.");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      loge("failed to start a server.");
      e.printStackTrace();
    }
  }


}
