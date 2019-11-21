package uci.mswe.swe242p.ex3_tcp_file_server;

import static uci.mswe.swe242p.ex3_tcp_file_server.Logger.loge;
import static uci.mswe.swe242p.ex3_tcp_file_server.Logger.logi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

class ResponseTask implements Callable<Void> {

  private Socket clientSocket;
  private String clientAddressAndPort;

  ResponseTask(Socket socket) {
    this.clientSocket = socket;
    this.clientAddressAndPort = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
  }

  public static void sendEndOfResponse(PrintWriter out) {
    out.println(Messages.END_OF_RESPONSE.toString());
  }


  @Override
  public Void call() throws Exception {
    try {
      var is = clientSocket.getInputStream();
      var isr = new InputStreamReader(is);
      var fromClient = new BufferedReader(isr);

      var os = clientSocket.getOutputStream();
      var toClient = new PrintWriter(os, true);

      // Wait for user input
      // in.readLine() will do the listening job
      var command = "";
      while ((command = fromClient.readLine()) != null) {
        logi("received command \"" + command + "\" from " + clientAddressAndPort);
        switch (command) {
          case "index": {
            try {
              // ! https://stackabuse.com/java-list-files-in-a-directory/
              // ! https://www.baeldung.com/java-list-directory-files
              // ! https://www.mkyong.com/java/java-how-to-list-all-files-in-a-directory/
              // Files.walk requires java 8

              // ! https://www.geeksforgeeks.org/double-colon-operator-in-java/
              /**
               * The double colon (::) operator, also known as method reference operator in Java, is
               * used to call a method by referring to it with the help of its class directly. They
               * behave exactly as the lambda expressions. The only difference it has from lambda
               * expressions is that this uses direct reference to the method by name instead of
               * providing a delegate to the method.
               */

              toClient.println("Index of " + TCPFileServer.folderPath.toAbsolutePath().toString());

              Files.walk(TCPFileServer.folderPath, 1) // depth = 1
                  .filter(Files::isRegularFile) // ignore folders
                  .map(Path::getFileName) //
                  .map(Path::toString) //
                  .collect(Collectors.toList()) //
                  .forEach(toClient::println);

              // customized message to end response
              sendEndOfResponse(toClient);
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
              var filePath =
                  Path.of(TCPFileServer.folderPath.toAbsolutePath().toString() + "\\" + fileName);
              System.out.println(filePath);
              /**
               * Files.notExists
               *
               * Note that this method is not the complement of the exists method. Where it is not
               * possible to determine if a file exists or not then both methods return false. As
               * with the exists method, the result of this method is immediately outdated. If this
               * method indicates the file does exist then there is no guarantee that a subsequent
               * attempt to create the file will succeed. Care should be taken when using this
               * method in security sensitive applications.
               */
              if (Files.notExists(filePath)) {
                toClient.println(Messages.ERROR.toString());
                toClient.println("File not found.");
                sendEndOfResponse(toClient);
              } else {
                toClient.println(Messages.OK.toString());
                Files.lines(filePath).forEach(toClient::println); // read file and send to client
                sendEndOfResponse(toClient);
              }
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
        clientSocket.close();
        logi("socket disconnected from " + clientAddressAndPort);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
