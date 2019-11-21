package uci.mswe.swe242p.ex4_udp_file_server;

import static uci.mswe.swe242p.ex3_tcp_file_server.Logger.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

public class UDPFileServer {

  private static final int PORT = 8000;
  final static int READ_TIMEOUT = 1000;

  static final int IN_BUFFER_SIZE = 1024; // That is the longest size of a command
  static final int OUT_BUFFER_SIZE = 8192;
  // see page 402-403 on Java Network Programming
  /**
   * https://stackoverflow.com/questions/3396813/message-too-long-for-udp-socket-after-setting-sendbuffersize
   *
   * The limit on a UDP datagram payload in IPv4 is 65535-28=65507 bytes, and the practical limit is
   * the MTU of the path which is more like 1460 bytes if you're lucky.
   */

  static String folderPathString = "";
  static Path folderPath = null;

  static DatagramSocket serverSocket = null;

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
          var packetFromClient = new DatagramPacket(new byte[IN_BUFFER_SIZE], IN_BUFFER_SIZE);
          serverSocket.receive(packetFromClient); // blocking IO

          logit("packet received from " + //
              packetFromClient.getAddress().getHostAddress() + ":" + packetFromClient.getPort());

          pool.submit(new ResponseTask(packetFromClient));

          Thread.sleep(2000);
          // ! let the sub-thread receive ACK
          // ? then it becomes a false multi-threaded program..

          // to realize a true multi-threaded server,
          // different threads should have their own sockets

          // SO the concept is:

          // main: listening on port 8000, receiving commands from clients
          // then the main thread tells the client to connect with a newly generated server port

          // sub: listening on a newly generated server port, for receiving ACK from a client

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
