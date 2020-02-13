package edu.uci.swe242p.ex3_tcp_file_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPFileClient {
  final static String SERVER_ADDR = "localhost";
  final static int SERVER_PORT = 8000;
  final static int READ_TIMEOUT = 3000;

  public static void printResponse(BufferedReader in) {
    // we have as much time as READ_TIMEOUT to read from server
    var response = "";
    try {
      while ((response = in.readLine()) != null) {
        if (response.equals(Messages.END_OF_RESPONSE.toString())) {
          break;
        }
        // then print it out
        System.out.println(response);
      }
    } catch (SocketTimeoutException e) {
      // ignore
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.println("TCPFileClient v1.0 by Junxian Chen");
    System.out.println("Trying to connect " + SERVER_ADDR + ":" + SERVER_PORT + " ...");

    try (var socket = new Socket(SERVER_ADDR, SERVER_PORT)) {
      System.out.println("Socket connected to " + SERVER_ADDR + ":" + SERVER_PORT);

      socket.setSoTimeout(READ_TIMEOUT);

      var is = socket.getInputStream();
      var isr = new InputStreamReader(is);
      var fromServer = new BufferedReader(isr);

      var os = socket.getOutputStream();
      var toServer = new PrintWriter(os, true);

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
            toServer.println("index");
            // print response from server
            printResponse(fromServer);
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
            // ! https://stackoverflow.com/questions/4814040/allowed-characters-in-filename
            // Please assume file names have no spaces
            //
            // File names with spaces have to use double quotes:
            // if (command.matches("^get\\s+\"(.+)\"\\s*$")) {
            // var filename = '"' + command.split("\"")[1] + '"';
            // System.out.println(filename);
            // }
            //
            // Or just allow anything except spaces:
            if (command.matches("^get\\s+([^\\s]+)\\s*$")) {
              var filename = command.split("\\s+")[1];
              toServer.println("get " + filename);
              printResponse(fromServer);
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
    }
  }
}
