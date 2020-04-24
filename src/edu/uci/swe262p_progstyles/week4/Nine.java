package edu.uci.swe262p_progstyles.week4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-04-21.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/10-the-one">the-one</a>
 */

@SuppressWarnings("ALL")
public class Nine {

    private static class Monad {
        private Object object;

        public Monad(String[] object) {
            this.object = object;
        }

        public Monad bind(Function<Object, Object> function) {
            object = function.apply(object);
            return this;
        }

        public void print() {
            System.out.println(object);
        }
    }

    private static Object buildPathFromArguments(Object object) {
        final String[] args = (String[]) object;

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

        return path;
    }

    private static Object buildLinesFromPath(Object object) {
        final Path path = (Path) object;
        try {
            return Files.lines(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static Object buildFrequencyMapFromLines(Object object) {
        final Stream<String> lines = (Stream<String>) object;

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

        return freqMap;
    }

    private static Object buildSortedResultFromMap(Object object) {
        final HashMap<String, Integer> freqMap = (HashMap<String, Integer>) object;

        // sort results
        List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(freqMap.entrySet());
        descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // print first 25 words
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 25; ++i) {
            final Map.Entry<String, Integer> entry = descendingList.get(i);
            result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
        }

        return result;
    }

    public static void main(String[] args) {
        new Monad(args)
                .bind(Nine::buildPathFromArguments)
                .bind(Nine::buildLinesFromPath)
                .bind(Nine::buildFrequencyMapFromLines)
                .bind(Nine::buildSortedResultFromMap)
                .print();
    }
}