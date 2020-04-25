package edu.uci.swe262p_progstyles.week4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-04-21.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/09-kick-forward">kick-forward</a>
 */

public class Eight {

    private static void buildPathFromArguments(String[] args,
                                               BiConsumer<Path,
                                                       BiConsumer<Stream<String>,
                                                               BiConsumer<HashMap<String, Integer>,
                                                                       BiConsumer<List<Map.Entry<String, Integer>>,
                                                                               Consumer<Object>>>>> function) {
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

        function.accept(path, Eight::buildFrequencyMapFromLines);
    }

    private static void buildLinesFromPath(Path path,
                                           BiConsumer<Stream<String>,
                                                   BiConsumer<HashMap<String, Integer>,
                                                           BiConsumer<List<Map.Entry<String, Integer>>,
                                                                   Consumer<Object>>>> function) {
        try {
            function.accept(Files.lines(path), Eight::buildSortedListFromMap);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void buildFrequencyMapFromLines(Stream<String> lines,
                                                   BiConsumer<HashMap<String, Integer>,
                                                           BiConsumer<List<Map.Entry<String, Integer>>,
                                                                   Consumer<Object>>> function) {
        // load stop words
        final String PATH_STOP_WORDS = "data/swe262p/stop_words.txt";
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
        HashMap<String, Integer> freqMap = new HashMap<>();
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

        function.accept(freqMap, Eight::printTop25);
    }

    private static void buildSortedListFromMap(HashMap<String, Integer> freqMap,
                                               BiConsumer<List<Map.Entry<String, Integer>>,
                                                       Consumer<Object>> function) {
        // sort results
        List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(freqMap.entrySet());
        descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        function.accept(descendingList, Eight::noOperation);
    }

    private static void printTop25(List<Map.Entry<String, Integer>> descendingList, Consumer<Object> function) {
        // print first 25 words
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 25; ++i) {
            final Map.Entry<String, Integer> entry = descendingList.get(i);
            result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
        }
        System.out.println(result);

        function.accept(null);
    }

    private static void noOperation(Object object) {
    }

    public static void main(String[] args) {
        buildPathFromArguments(args, Eight::buildLinesFromPath);
    }
}