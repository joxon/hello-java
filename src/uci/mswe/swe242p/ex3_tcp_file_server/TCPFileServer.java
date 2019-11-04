package uci.mswe.swe242p.ex3_tcp_file_server;

import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
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
        var os = connection.getOutputStream();
        var osw = new OutputStreamWriter(os);
        osw.write("hello\n");
        osw.flush();
      } catch (Exception e) {
        loge("failed to run ResponseTask.");
        e.printStackTrace();
      } finally {
        connection.close();
      }
      return null;
    }
  }

  public static String dateTime() {
    // yyyy-mm-ddTHH:mm:ss
    return LocalDateTime.now().toString().split("[.]")[0];
  }

  public static void logi(String s) {
    System.out.println(dateTime() + " INFO: " + s);
  }

  public static void loge(String s) {
    System.err.println(dateTime() + " ERROR: " + s);
  }

  public static void main(String[] args) {
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
