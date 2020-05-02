package edu.uci.swe262p_progstyles.week5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-05-01.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/15-hollywood">15-hollywood</a>
 */
public class Fourteen {

    @FunctionalInterface
    private interface Procedure {
        void run();
    }

    private static final class WordFrequencyFramework {

        private final List<Consumer<String>> loadEventHandlers = new ArrayList<>();
        private final List<Procedure> doWorkEventHandlers = new ArrayList<>();
        private final List<Procedure> endEventHandlers = new ArrayList<>();

        public void registerForLoadEvent(Consumer<String> handler) {
            loadEventHandlers.add(handler);
        }

        public void registerForDoWorkEvent(Procedure handler) {
            doWorkEventHandlers.add(handler);
        }

        public void registerForEndEvent(Procedure handler) {
            endEventHandlers.add(handler);
        }

        public void run(String spath) {
            loadEventHandlers.forEach(handler -> handler.accept(spath));
            doWorkEventHandlers.forEach(Procedure::run);
            endEventHandlers.forEach(Procedure::run);
        }
    }


    private static final class TextLinesStorage {

        private Stream<String> lines;
        final private StopWordsFilter filter;
        final private List<Consumer<String>> wordEventHandler = new ArrayList<>();

        TextLinesStorage(WordFrequencyFramework framework, StopWordsFilter filter) {
            framework.registerForLoadEvent(this::load);
            framework.registerForDoWorkEvent(this::produceWords);
            this.filter = filter;
        }

        private void load(String spath) {
            try {
                lines = Files.lines(Path.of(spath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void produceWords() {
            // start counting
            lines.forEach(line -> {
                try {
                    String[] words = line.split("[^a-zA-Z]+");
                    for (String word : words) {
                        String w = word.toLowerCase();
                        if (!filter.isStopWord(w) && w.length() > 1) {
                            wordEventHandler.forEach(handler -> handler.accept(w));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        public void registerForWordEvent(Consumer<String> handler) {
            this.wordEventHandler.add(handler);
        }
    }

    private static final class StopWordsFilter {

        private final Set<String> stopWords = new HashSet<>();

        StopWordsFilter(WordFrequencyFramework framework) {
            framework.registerForLoadEvent(this::load);
        }

        private void load(String unused) {
            // load stop words
            final String PATH_STOP_WORDS = "data/swe262p/stop_words.txt";
            try {
                final byte[] bytes = Files.readAllBytes(Path.of(PATH_STOP_WORDS));
                final String[] words = new String(bytes).split(",");
                stopWords.addAll(Arrays.asList((words)));
            } catch (IOException e) {
                System.err.println("Error reading stop_words.txt");
                System.exit(1);
            }
        }

        public boolean isStopWord(String w) {
            return stopWords.contains(w);
        }
    }

    private static final class WordFrequencyCounter {

        private final HashMap<String, Integer> frequencyMap = new HashMap<>();

        WordFrequencyCounter(WordFrequencyFramework framework, TextLinesStorage storage) {
            storage.registerForWordEvent(this::increment);
            framework.registerForEndEvent(this::print);
        }

        private void increment(String w) {
            if (frequencyMap.containsKey(w)) {
                frequencyMap.put(w, frequencyMap.get(w) + 1);
            } else {
                frequencyMap.put(w, 1);
            }
        }

        public void print() {
            // sort results
            final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(frequencyMap.entrySet());
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

    private static final class WordsWithZCounter {
        private final Set<String> wordsWithZ = new HashSet<>();

        WordsWithZCounter(WordFrequencyFramework framework, TextLinesStorage storage) {
            storage.registerForWordEvent(this::add);
            framework.registerForEndEvent(this::print);
        }

        private void add(String w) {
            if (w.contains("z")) {
                wordsWithZ.add(w);
            }
        }

        public void print() {
            System.out.println("Unique non-stop words with 'Z': " + wordsWithZ.size());
        }
    }

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

    public static void main(String[] args) {
        validateArguments(args);
        WordFrequencyFramework framework = new WordFrequencyFramework();
        StopWordsFilter stopWordsFilter = new StopWordsFilter(framework);
        TextLinesStorage textLinesStorage = new TextLinesStorage(framework, stopWordsFilter);
        new WordFrequencyCounter(framework, textLinesStorage);
        new WordsWithZCounter(framework, textLinesStorage);
        framework.run(args[0]);
    }

}