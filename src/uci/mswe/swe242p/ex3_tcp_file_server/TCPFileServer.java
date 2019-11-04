package uci.mswe.swe242p.ex3_tcp_file_server;

import static uci.mswe.swe242p.ex3_tcp_file_server.LogUtils.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * TCPFileServer
 */
public class TCPFileServer {

  private static final int PORT = 8000;

  private static class ResponseTask implements Callable<Void> {

    private Socket connection;

    ResponseTask(Socket connection) {
      this.connection = connection;
    }

    @Override
    public Void call() throws Exception {
      try {
        var is = connection.getInputStream();
        var isr = new InputStreamReader(is);
        var in = new BufferedReader(isr);

        var os = connection.getOutputStream();
        var out = new PrintWriter(os, true);

        // Wait for user input
        // in.readLine() will do the listening job
        var command = "";
        while ((command = in.readLine()) != null) {
          switch (command) {
            case "index": {
              // TODO: List the files on server
              out.println("files");
            }
              break;

            default: {
              if (command.startsWith("get ")) {
                var filename = command.split(" ")[1];

                // TODO: Send the file content to client
                out.println("OK");
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
        connection.close();
      }
      return null;
    }
  }

  public static void main(String[] args) {
    // TODO: Parse folder path

    var pool = Executors.newFixedThreadPool(50);
    try (var server = new ServerSocket(PORT)) {
      logi("server listening on port " + PORT);
      while (true) {
        try {
          var connection = server.accept(); // blocking IO

          var remoteAddress = connection.getInetAddress().getHostAddress();
          var remotePort = connection.getPort();
          logi("socket connected from " + remoteAddress + ":" + remotePort);

          pool.submit(new ResponseTask(connection));
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
