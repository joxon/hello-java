package uci.mswe.swe242p.ex3_tcp_file_server;

import static uci.mswe.swe242p.ex3_tcp_file_server.LogUtils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * TCPFileServer
 */
public class TCPFileServer {

  private static final int PORT = 8000;

  public static String folderPathString = "";
  public static Path folderPath = null;

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Please input ONE folder path.");
      return;
    }

    folderPathString = args[0];
    folderPath = Paths.get(folderPathString);

    if (!Files.isDirectory(folderPath)) {
      System.err.println("Path is not valid.");
      return;
    }

    logi("server monitoring folder " + folderPathString);

    var pool = Executors.newFixedThreadPool(50);
    try (var server = new ServerSocket(PORT)) {
      logi("server listening on port " + PORT);
      while (true) {
        try {
          var connection = server.accept(); // blocking IO

          var remoteAddress = connection.getInetAddress().getHostAddress();
          var remotePort = connection.getPort();
          var remoteAddressAndPort = remoteAddress + ":" + remotePort;
          logi("socket connected from " + remoteAddressAndPort);

          pool.submit(new ResponseTask(connection, remoteAddressAndPort));
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

  private static class ResponseTask implements Callable<Void> {

    private Socket connection;
    private String remoteAddressAndPort;

    ResponseTask(Socket connection, String addr) {
      this.connection = connection;
      this.remoteAddressAndPort = addr;
    }

    @Override
    public Void call() throws Exception {
      try {
        var is = connection.getInputStream();
        var isr = new InputStreamReader(is);
        var fromClient = new BufferedReader(isr);

        var os = connection.getOutputStream();
        var toClient = new PrintWriter(os, true);

        // Wait for user input
        // in.readLine() will do the listening job
        var command = "";
        while ((command = fromClient.readLine()) != null) {
          logi("received command \"" + command + "\" from " + remoteAddressAndPort);
          switch (command) {
            case "index": {
              try {
                // ! https://stackabuse.com/java-list-files-in-a-directory/
                // ! https://www.baeldung.com/java-list-directory-files
                // ! https://www.mkyong.com/java/java-how-to-list-all-files-in-a-directory/
                // Files.walk requires java 8

                // ! https://www.geeksforgeeks.org/double-colon-operator-in-java/
                /**
                 * The double colon (::) operator, also known as method reference operator in Java,
                 * is used to call a method by referring to it with the help of its class directly.
                 * They behave exactly as the lambda expressions. The only difference it has from
                 * lambda expressions is that this uses direct reference to the method by name
                 * instead of providing a delegate to the method.
                 */
                toClient.println("Index of " + folderPath.toAbsolutePath().toString());
                Files.walk(folderPath, 1) // depth = 1
                    .filter(Files::isRegularFile) //
                    .map(Path::getFileName) //
                    .map(Path::toString) //
                    .collect(Collectors.toList()) //
                    .forEach(toClient::println);
                // customized message to end response
                toClient.println("EOR");
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
              break;

            default: {
              if (command.startsWith("get ")) {
                var filename = command.split(" ")[1];

                // TODO: Send the file content to client
                toClient.println("OK");
              } else {
                loge("unknown command received.");
              }
            }
              break;
          }
        }
      } catch (Exception e) {
        loge("an exception occured when running ResponseTask.");
        e.printStackTrace();
      } finally {
        try {
          connection.close();
          logi("socket disconnected from " + remoteAddressAndPort);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return null;
    }
  }

}
