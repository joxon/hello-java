package uci.mswe.swe242p.ex3_tcp_file_server;

import java.io.InputStreamReader;
import java.net.Socket;

/**
 * TCPFileClient
 */
public class TCPFileClient {
  final static String SERVER_ADDR = "localhost";
  final static int SERVER_PORT = 8000;

  public static void main(String[] args) {
    try (var socket = new Socket(SERVER_ADDR, SERVER_PORT)) {
      socket.setSoTimeout(3000);
      var is = socket.getInputStream();
      var isr = new InputStreamReader(is);
      var str = new StringBuilder();
      int ch = 0;
      while ((ch = isr.read()) != -1) {
        str.append((char) ch);
      }
      System.out.println(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
