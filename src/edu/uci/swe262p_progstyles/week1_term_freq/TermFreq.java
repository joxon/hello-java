package edu.uci.swe262p_progstyles.week1_term_freq;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Joxon on 2020-03-30.
 */
public class TermFreq {
  public static void main(String[] args) {
    // process arguments
    if (args.length != 1) {
      System.err.println("Please provide exactly ONE argument. Current: " + args.length);
      System.exit(1);
    }

    final Path path = Path.of(args[0]);
    if (!path.toFile().exists()) {
      System.err.println(path + " does not exist.");
      System.exit(1);
    }

    // load stop words
    final String PATH_STOP_WORDS = "data/swe262p_week1/stop_words.txt";
    final Set<String> stopWords = new HashSet<>();
    try {
      final byte[] bytes = Files.readAllBytes(Path.of(PATH_STOP_WORDS));
      final String[] words = new String(bytes).split(",");
      stopWords.addAll(Arrays.asList((words)));
    } catch (IOException e) {
      System.err.println("Error reading stop_words.txt");
      System.exit(1);
    }

    // start counting
    final HashMap<String, Integer> freqMap = new HashMap<>();
    try {
      try (Stream<String> lines = Files.lines(path)) {
        lines.forEach(line -> {
          // Be careful. Some words are wrapped with underscores.
          // Examples: _very_ | _as good_ | _Mr. Darcy_
          // \w (word character) = [a-zA-Z0-9_]
          // \W (non-word-character) = [^a-zA-Z0-9_]
          String[] words = line.split("[^a-zA-Z]+");
          for (String word : words) {
            String w = word.toLowerCase();
            if (!stopWords.contains(w) && w.length() > 1) {
              if (freqMap.containsKey(w)) {
                freqMap.put(w, freqMap.get(w) + 1);
              } else {
                freqMap.put(w, 1);
              }
            }
          }
        });
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }

    // sort results
    final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(freqMap.entrySet());
    descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

    // print first 25 words
    final StringBuilder result = new StringBuilder();
    for (int i = 0; i < 25; ++i) {
      final Map.Entry<String, Integer> entry = descendingList.get(i);
      result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
    }
    System.out.println(result);
  }
}
