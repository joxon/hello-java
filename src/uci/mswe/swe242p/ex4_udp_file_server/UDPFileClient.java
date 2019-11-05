package uci.mswe.swe242p.ex4_udp_file_server;

import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class UDPFileClient {
  final static String SERVER_ADDR = "localhost";
  static InetAddress SERVER_IADDR = null;
  final static int SERVER_PORT = 8000;
  final static int READ_TIMEOUT = 3000;

  private static final int IN_BUFFER_SIZE = 8192;
  // private static final int OUT_BUFFER_SIZE = 1024; // see page 402-403 on Java

  static DatagramSocket clientSocket = null;

  public static void printResponse(DatagramPacket serverPacket) {
    // clear buffer
    serverPacket.setData(new byte[IN_BUFFER_SIZE], 0, IN_BUFFER_SIZE);
    try {
      clientSocket.receive(serverPacket);
      System.out.println(new String(serverPacket.getData()).replace("\0", ""));
    } catch (SocketTimeoutException e) {
      // ignore
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void sendRequestString(String req) throws IOException {
    var bytes = req.getBytes();
    clientSocket.send(new DatagramPacket(bytes, bytes.length, SERVER_IADDR, SERVER_PORT));
  }

  public static void main(String[] args) {
    System.out.println("UDPFileClient v1.0 by Junxian Chen");

    try {
      clientSocket = new DatagramSocket(0);
      System.out.println("Socket opened at port " + clientSocket.getLocalPort());

      clientSocket.setSoTimeout(READ_TIMEOUT);

      SERVER_IADDR = InetAddress.getByName(SERVER_ADDR);

      var serverPacket = new DatagramPacket(new byte[0], 0);

      var console = System.console();
      final String PROMPT = String.join("\n", //
          "help      : Print this prompt;", //
          "index     : List the files on server;", //
          "get <file>: Fetch the file from server;", //
          "q         : Quit" //
      );
      System.out.println(PROMPT);
      var command = console.readLine("client> ");
      while (true) {
        switch (command) {
          case "":
            break;

          case "help":
            System.out.println(PROMPT);
            break;

          case "index": {
            // send the command "index" to server
            sendRequestString("index");
            // print response from server
            printResponse(serverPacket);
          }
            break;

          case "get":
          case "get ":
            System.out.println("Please input ONE file name.");
            break;

          case "q":
            System.out.println("Bye.");
            return;

          default: {
            if (command.matches("^get\\s+([^\\s]+)\\s*$")) {
              var filename = command.split("\\s+")[1];
              sendRequestString("get " + filename);
              printResponse(serverPacket);
            } else {
              System.out.println("Unknown command.");
            }
          }
            break;
        }
        command = console.readLine("client> ");
      }
    } catch (ConnectException e) {
      System.err.println("Connection timeout.");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        clientSocket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
