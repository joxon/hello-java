package uci.mswe.swe244p.ex1_hello_world;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class MultiThreadedHelloWorld {

  static final String PROMPT = "Here are your options:\n" //
      + "a - Create a new thread\n" //
      + "b - Stop a given thread (e.g. \"b 2\" kills thread 2)\n" //
      + "c - Stop all threads and exit this program\n" //
      + "> ";

  static String result = "Waiting for input...";

  static final int PRINT_INTERVAL = 1000;
  static final int HELLO_INTERVAL = 2000;

  static Integer threadId = Integer.valueOf(0);

  static HashMap<Integer, HelloThread> threads = new HashMap<Integer, HelloThread>();
  // * HashSet is a set, e.g. {1,2,3,4,5}
  // * HashMap is a key -> value (key to value) map, e.g. {a -> 1, b -> 2, c -> 2, d -> 1}

  private static class PrintThread extends Thread {

    static void printAndSleep() throws InterruptedException, IOException {
      threads.forEach((id, t) -> {
        System.out.println(t.getMsg());
      });
      System.out.println("Result: " + result);
      System.out.print(PROMPT);
      sleep(PRINT_INTERVAL);
    }

    @Override
    public void run() {
      try {
        final var osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac") || osName.contains("darwin")) {
          while (true) {
            Runtime.getRuntime().exec("clear");
            printAndSleep();
          }
        } else if (osName.contains("win")) {
          while (true) {
            // Runtime.getRuntime().exec("cls");
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            printAndSleep();
          }
        } else if (osName.contains("nix")) {
          while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            printAndSleep();
          }
        }
      } catch (Exception e) {

      }
    }
  }

  private static class HelloThread extends Thread {

    private String id;
    private String msg;

    HelloThread(String id) {
      this.id = id;
      this.setMsg("Thread " + id + " initialized at " + getTime());
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public String getTime() {
      return LocalDateTime.now().toLocalTime().toString();
    }

    @Override
    public void run() {
      try {
        while (true) {
          setMsg("Hello World! I'm thread " + id + ". The time is " + getTime());
          sleep(HELLO_INTERVAL);
        }
      } catch (InterruptedException e) {
        setMsg("Thread " + id + " was interrupted at " + getTime());
      }
    }
  }

  private static void printAndSave(String s) {
    result = s;
    System.out.println(s);
  }

  public static void main(String[] args) {

    System.out.println("Starting printThread...");
    var printThread = new PrintThread();
    printThread.start();

    while (true) {
      // https://stackoverflow.com/questions/1066318/how-to-read-a-single-char-from-the-console-in-java-as-the-user-types-it
      // TODO: Input is being cleared every time... But it is in the buffer that cannot be fetched
      var command = System.console().readLine();
      switch (command) {
        case "a": {
          System.out.println("Creating thread " + threadId);

          var thread = new HelloThread(threadId.toString());
          threads.put(threadId, thread);
          // Do NOT use thread.run()
          thread.start();

          result = "Thread " + threadId + " is created!";
          ++threadId;
        }
          break;

        case "b":
        case "b ":
          printAndSave("Please input ONE thread ID.");
          break;

        case "c": {
          // Traditional method:
          //
          // for (var thread : threads.values()) {
          // thread.interrupt();
          // }
          System.out.println("Stopping printThread");
          printThread.interrupt();

          threads.forEach((tid, thread) -> {
            System.out.println("Stopping thread " + tid);
            thread.interrupt();
          });

          System.out.println("All threads stopped. Bye.");
          return;
        }

        default: {
          if (command.matches("^b\\s+([^\\s]+)\\s*$")) {
            var tid = command.split("\\s+")[1];
            var thread = threads.get(Integer.valueOf(tid));
            if (thread == null) {
              printAndSave("Invalid thread ID " + tid);
            } else {
              System.out.println("Stopping thread " + tid);
              thread.interrupt();
              result = "Thread " + tid + " is now stopped";
            }
          } else {
            printAndSave("Unknown command \"" + command + '"');
          }
        }
          break;
      }
    }
  }
}
