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
    if (args.length != 1) {
      System.err.println("Please provide exactly ONE argument.");
      return;
    }

    // load stop words
    final String DATA_DIR = "data/swe262p_week1/";
    final String PATH_STOP_WORDS = DATA_DIR + "stop_words.txt";
    final Set<String> STOP_WORDS = new HashSet<>();
    try {
      final byte[] bytes = Files.readAllBytes(Path.of(PATH_STOP_WORDS));
      final String[] words = new String(bytes).split(",");
      STOP_WORDS.addAll(Arrays.asList((words)));
    } catch (IOException e) {
      System.err.println("Error reading stop_words.txt");
      return;
    }

    // start counting
    final HashMap<String, Integer> freqMap = new HashMap<>();
    try {
      try (Stream<String> lines = Files.lines(Path.of(args[0]))) {
        lines.forEach(line -> {
          String[] words = line.split("\\W+");
          for (String word : words) {
            String w = word.toLowerCase();
            if (!STOP_WORDS.contains(w) && w.length() > 1) {
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
      return;
    }

    // sort results
    List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(freqMap.entrySet());
    descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

    // print first 25 words
    StringBuilder result = new StringBuilder();
    int i = 1;
    for (Map.Entry<String, Integer> entry : descendingList) {
      result.append(entry.getKey()).append("  -  ").append(entry.getValue());
      if (i == 25) {
        break;
      } else {
        result.append("\n");
        ++i;
      }
    }
    System.out.println(result);
  }
}
