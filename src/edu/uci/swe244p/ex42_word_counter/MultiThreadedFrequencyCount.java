package edu.uci.swe244p.ex42_word_counter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * Simple word frequency program, multi-threaded
 *
 * @author Crista Lopes
 * @author Junxian Chen
 *
 */
public class MultiThreadedFrequencyCount {
  private static final int CPU_COUNT = 4;

  private static final String DATA_DIR = "data/swe244p_ex42/";

  private static final List<String> stopWords = new ArrayList<String>();

  static final class Counter {
    private HashMap<String, Integer> frequencies = new HashMap<String, Integer>();

    private void processFile(Path path) {
      var tname = Thread.currentThread().getName();
      System.out.println(tname + ": Started counting " + path);

      try (Stream<String> lines = Files.lines(path /* Paths.get(filename) */ )) {
        lines.forEach(line -> processLine(line));
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println(tname + ": Ended counting " + path);
    }

    // Keep only the non stop words with 3 or more characters
    private void processLine(String line) {
      String[] words = line.split("\\W+");
      for (String word : words) {
        String w = word.toLowerCase();
        if (!stopWords.contains(w) && w.length() > 2) {
          // ! read
          if (frequencies.containsKey(w)) {
            // ! read then write
            frequencies.put(w, frequencies.get(w) + 1);
          } else {
            // ! write
            frequencies.put(w, 1);
          }
        }
      }
    }

    private List<Map.Entry<String, Integer>> sort() {
      var list = new ArrayList<Map.Entry<String, Integer>>(frequencies.entrySet());
      Collections.sort(list, // (a, b) -> a.getValue().compareTo(b.getValue())
          new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
              return o2.getValue().compareTo(o1.getValue());
            }
          }//
      );
      return list;
    }

    // ! big hint: we need to get freq
    private HashMap<String, Integer> getFrequencies() {
      return frequencies;
    }

    // ! big hint: we need to merge
    public void merge(Counter other) {
      other.getFrequencies().forEach((k, v) -> frequencies.merge(k, v, Integer::sum));
    }
    // ! big hint: so we need to get freq from different counters, then merge them into one?


    // Only the top 40 words that are 3 or more characters
    public String toString() {
      List<Map.Entry<String, Integer>> sortedEntries = sort();
      StringBuilder result = new StringBuilder("---------- Word counts (top 40) -----------\n");
      int i = 1;
      for (Map.Entry<String, Integer> entry : sortedEntries) {
        result.append("[" + i + "] \"" + entry.getKey() + "\": " + entry.getValue());
        if (i == 40) {
          break;
        } else {
          result.append("\n");
          ++i;
        }
      }
      return result.toString();
    }

  }

  private static void loadStopWords() {
    try {
      stopWords.addAll(//
          Arrays.asList(//
              new String(//
                  Files.readAllBytes(//
                      Paths.get(DATA_DIR + "stop_words")))//
                          .split(",")));
    } catch (IOException e) {
      System.out.println("Error reading stop_words");
    }
  }

  public static void main(String[] args) {

    loadStopWords();

    var pool = Executors.newFixedThreadPool(CPU_COUNT);
    var mainCounter = new Counter();
    var subCounters = new ArrayList<Counter>(CPU_COUNT);

    long start = System.nanoTime();

    try {
      Files.walk(Paths.get(DATA_DIR))//
          .filter(path -> path.toString().endsWith(".txt"))//
          .forEach(path -> {
            var subCounter = new Counter();
            subCounters.add(subCounter);
            pool.submit(() -> subCounter.processFile(path));
          });
      /**
       * page 93
       *
       * The shutdown method initiates a graceful shutdown: no new tasks are accepted but previously
       * submitted tasks are allowed to complete  including those that have not yet begun
       * execution.
       *
       * The shutdownNow method initiates an abrupt shutdown: it attempts to cancel outstanding
       * tasks and does not start any tasks that are queued but not begun.
       */
      pool.shutdown();
      pool.awaitTermination(10, TimeUnit.SECONDS);
    } catch (Exception e) {
      e.printStackTrace();
    }
    subCounters.forEach((sub) -> mainCounter.merge(sub));

    long end = System.nanoTime();
    long elapsed = end - start;

    System.out.println(mainCounter);
    System.out.println("MTFC Elapsed time: " + elapsed / 1e6 + "ms"); // 1000000 == 1e6 != 10e6
  }
}

/**
 * Summary of Part I
 *
 * • We've covered a lot of material so far! The following "concurrency cheat sheet" summarizes the
 * main concepts and rules presented in Part I.
 *
 * • It's the mutable state, stupid. All concurrency issues boil down to coordinating access to
 * mutable state. The less mutable state, the easier it is to ensure thread safety.
 *
 * • Make fields final unless they need to be mutable.
 *
 * • Immutable objects are automatically thread-safe. Immutable objects simplify concurrent
 * programming tremendously. They are simpler and safer, and can be shared freely without locking or
 * defensive copying.
 *
 * • Encapsulation makes it practical to manage the complexity. You could write a thread-safe
 * program with all data stored in global variables, but why would you want to? Encapsulating data
 * within objects makes it easier to preserve their invariants; encapsulating synchronization within
 * objects makes it easier to comply with their synchronization policy. • Guard each mutable
 * variable with a lock.
 *
 * • Guard all variables in an invariant with the same lock.
 *
 * • Hold locks for the duration of compound actions.
 *
 * • A program that accesses a mutable variable from multiple threads without synchronization is a
 * broken program.
 *
 * • Don't rely on clever reasoning about why you don't need to synchronize.
 *
 * • Include thread safety in the design processor explicitly document that your class is not
 * thread-safe. Document your synchronization policy.
 */
