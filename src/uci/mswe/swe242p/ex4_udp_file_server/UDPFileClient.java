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
  final static int READ_TIMEOUT = 1000;

  private static final int IN_BUFFER_SIZE = 8192;
  // private static final int OUT_BUFFER_SIZE = 1024; //
  /**
   * see page 401-403 on Java Network Pragramming, 4th edition
   *
   * The constructor doesn’t care how large the buffer is and would happily let you create a
   * DatagramPacket with megabytes of data. However, the underlying native network software is less
   * forgiving, and most native UDP implementations don’t support more than 8,192 bytes of data per
   * datagram. The theoretical limit for an IPv4 datagram is 65,507 bytes of data, and a
   * DatagramPacket with a 65,507-byte buffer can receive any possible IPv4 datagram without losing
   * data. IPv6 datagrams raise the theoretical limit to 65,536 bytes. In practice, however, many
   * UDP-based protocols such as DNS and TFTP use packets with 512 bytes of data per datagram or
   * fewer. The largest data size in common usage is 8,192 bytes for NFS. Almost all UDP datagrams
   * you’re likely to encounter will have 8K of data or fewer. In fact, many operating systems don’t
   * support UDP datagrams with more than 8K of data and either truncate, split, or discard larger
   * datagrams. If a large datagram is too big and as a result the network truncates or drops it,
   * your Java program won’t be notified of the problem. Consequently, you shouldn’t create Data
   * gramPacket objects with more than 8,192 bytes of data.
   *
   */
  static DatagramSocket clientSocket = null;

  public static void printResponse(DatagramPacket serverPacket) {
    // clear buffer? NOT NEEDED
    // serverPacket.setData(new byte[IN_BUFFER_SIZE], 0, IN_BUFFER_SIZE);
    var response = new StringBuilder();
    try {
      // in case of those files that are splitted
      // we have to keep listening from server
      // TODO packet ordering?
      while (true) {
        clientSocket.receive(serverPacket); // it sets data and *length* so no need to clear buffer
        response.append(new String(serverPacket.getData(), 0, serverPacket.getLength()));
      }
    } catch (SocketTimeoutException e) {
      // expected
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println(response);
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

      var serverPacket = new DatagramPacket(new byte[IN_BUFFER_SIZE], IN_BUFFER_SIZE);

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
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
