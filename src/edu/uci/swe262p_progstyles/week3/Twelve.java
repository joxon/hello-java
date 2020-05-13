package edu.uci.swe262p_progstyles.week3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-04-17.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/13-closed-maps">closed-maps</a>
 */

@SuppressWarnings("unchecked") // to suppress type cast warnings
public class Twelve {

    private static void validateArguments(String[] args) {
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
    }

    private static final Map<String, Object> textLinesManager = new HashMap<>(); // Map.of(); // produces immutable maps

    static {
        textLinesManager.put("lines", new Object()/* Stream<String>() */);
        textLinesManager.put("init", (Consumer<String>) spath -> {
            try {
                textLinesManager.put("lines", Files.lines(Path.of(spath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static final Map<String, Object> stopWordsManager = new HashMap<>();

    static {
        stopWordsManager.put("stopWords", new HashSet<>()/* Set<String>() */);
        stopWordsManager.put("init", (Runnable) () -> {
            // load stop words
            final String PATH_STOP_WORDS = "data/swe262p/stop_words.txt";
            try {
                final byte[] bytes = Files.readAllBytes(Path.of(PATH_STOP_WORDS));
                final String[] words = new String(bytes).split(",");
                ((Set<String>) (stopWordsManager.get("stopWords"))).addAll(Arrays.asList((words)));
            } catch (IOException e) {
                System.err.println("Error reading stop_words.txt");
                System.exit(1);
            }
        });
        stopWordsManager.put("isStopWord", (Function<String, Boolean>) w -> ((Set<String>) (stopWordsManager.get("stopWords"))).contains(w));
    }

    private static final Map<String, Object> frequencyMapManager = new HashMap<>();

    static {
        frequencyMapManager.put("frequencyMap", new HashMap<String, Integer>());
        frequencyMapManager.put("increment", (Consumer<String>) w -> {
            HashMap<String, Integer> frequencyMap = //
                    (HashMap<String, Integer>) frequencyMapManager.get("frequencyMap");
            if (frequencyMap.containsKey(w)) {
                frequencyMap.put(w, frequencyMap.get(w) + 1);
            } else {
                frequencyMap.put(w, 1);
            }
        });
    }

    public static void main(String[] args) {
        validateArguments(args);

        ((Consumer<String>) textLinesManager.get("init")).accept(args[0]);
        ((Runnable) stopWordsManager.get("init")).run();

        // start counting
        ((Stream<String>) textLinesManager.get("lines")).forEach(line -> {
            try {
                String[] words = line.split("[^a-zA-Z]+");
                for (String word : words) {
                    String w = word.toLowerCase();
                    if (!((Function<String, Boolean>) stopWordsManager.get("isStopWord")).apply(w) && w.length() > 1) {
                        ((Consumer<String>) frequencyMapManager.get("increment")).accept(w);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        frequencyMapManager.put("top25", (Runnable) () -> {
            // sort results
            final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(
                    ((HashMap<String, Integer>) frequencyMapManager.get("frequencyMap")).entrySet());
            descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            // print first 25 words
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < 25; ++i) {
                final Map.Entry<String, Integer> entry = descendingList.get(i);
                result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
            }
            System.out.println(result);
        });
        ((Runnable) frequencyMapManager.get("top25")).run();
    }
}