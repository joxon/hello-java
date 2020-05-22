package edu.uci.swe262p_progstyles.week8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-05-20.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/28-lazy-rivers">lazy-rivers</a>
 */
public class TwentySeven {

    // Set it to true to get debug output
    private static final boolean DEBUGGING = false;

    private static Iterable<Character> characters(String fileName) {
        return () -> { // Anonymous Iterable, overriding the iterator() method
            try {
                return Files.lines(Path.of(fileName)) // Stream<String>
                        .flatMap(line -> line.concat("\n") // HAVE TO PUT '\n' BACK!!!
                                .chars() // IntStream
                                .mapToObj(intChar -> (char) intChar)  // Stream<Character>
                        ) // Stream<Character>
                        .iterator();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
                return Stream.of((Character) null).iterator(); // Just a candy for the compiler. It will never run.
            }
        };
    }

    private static Iterable<String> allWords(String fileName) {
        // https://openjdk.java.net/projects/amber/LVTIstyle.html
        // If you decide to use var with diamond or a generic method,
        // you should ensure that method or constructor arguments
        // provide enough type information so that the inferred type matches your intent.
        // Otherwise, avoid using both var with diamond or a generic method
        // in the same declaration.
        Stream.Builder<String> words = Stream.builder();

        var isStartCharacter = true;
        var word = "";
        for (var ch : characters(fileName)) {
            if (isStartCharacter) {
                word = "";
                if (Character.isLetterOrDigit(ch)) {
                    word = (ch.toString().toLowerCase());
                    isStartCharacter = false;
                }
            } else {
                if (Character.isLetterOrDigit(ch)) {
                    word += (ch.toString().toLowerCase());
                } else {
                    isStartCharacter = true;
                    words.add(word);
                }
            }
        }

        return () -> words.build().iterator();
    }


    private static Iterable<String> nonStopWords(String fileName) throws IOException {
        Stream.Builder<String> words = Stream.builder();

        final var stopWords = Files.lines(Path.of("data/swe262p/stop_words.txt"))
                .flatMap(line -> Arrays.stream(line.split(",")))
                .collect(Collectors.toSet());
        stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));

        for (var word : allWords(fileName)) {
            if (!stopWords.contains(word)) {
                words.add(word);
            }
        }

        return () -> words.build().iterator();
    }

    private static Iterable<List<Map.Entry<String, Integer>>> countAndSort(String fileName) throws IOException {
        Stream.Builder<List<Map.Entry<String, Integer>>> sortedLists = Stream.builder();

        final var frequencyMap = new HashMap<String, Integer>();
        var i = 1;
        for (var word : nonStopWords(fileName)) {
            frequencyMap.put(word, frequencyMap.containsKey(word) ? frequencyMap.get(word) + 1 : 1);
            // For debugging: print results for every 5000 words
            if (DEBUGGING) {
                if (i % 5000 == 0) {
                    sortedLists.add(frequencyMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                            .limit(25)
                            .collect(Collectors.toUnmodifiableList()));
                }
                ++i;
            }
        }
        sortedLists.add(frequencyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .limit(25)
                .collect(Collectors.toUnmodifiableList()));

        return () -> sortedLists.build().iterator();
    }

    private static void validateArguments(String[] args) {
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

    public static void main(String[] args) throws IOException {
        validateArguments(args);

        for (var wordFrequencyList : countAndSort(args[0])) {
            System.out.print(DEBUGGING ? "-----------------------------\n" : "");
            for (var entry : wordFrequencyList.subList(0, Math.min(25, wordFrequencyList.size()))) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }
    }
}
