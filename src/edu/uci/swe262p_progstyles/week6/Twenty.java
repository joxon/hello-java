package edu.uci.swe262p_progstyles.week6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-05-06.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/21-constructivist">constructivist</a>
 */
public class Twenty {

    private static List<String> extractWords(String pathToFile) {
        if (/*!(pathToFile instanceof String) ||*/ pathToFile == null) {
            return List.of();
        }

        Stream<String> stringData;
        try {
            stringData = Files.lines(Path.of(pathToFile));
        } catch (IOException e) {
            System.err.println("I/O error when opening " + pathToFile + ": " + e.getMessage());
            return List.of();
        }

        final List<String> wordList = new ArrayList<>();
        stringData.forEach(line ->
                Arrays.stream(line.split("[\\W_]+"))
                        .filter(word -> word.length() > 1)
                        .map(String::toLowerCase)
                        .forEach(wordList::add));
        return wordList;
    }

    private static List<String> removeStopWords(List<String> wordList) {
        if (wordList == null /*!(wordList instanceof List)*/) {
            return List.of();
        }

        final var stopWords = new HashSet<String>();
        try {
            Files.lines(Path.of("data/swe262p/stop_words.txt"))
                    .forEach(line -> Collections.addAll(stopWords, line.split(",")));
        } catch (IOException e) {
            System.err.println("I/O error when opening stop_words.txt: " + e.getMessage());
            return List.of();
        }

        stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        return wordList.stream().filter(word -> !stopWords.contains(word)).collect(Collectors.toList());
    }

    private static Map<String, Integer> frequencies(List<String> wordList) {
        if (wordList == null /*!(wordList instanceof List)*/ || wordList.isEmpty()) {
            return Map.of();
        }

        final var wordFreq = new HashMap<String, Integer>();
        wordList.forEach(word -> {
            if (wordFreq.containsKey(word)) {
                wordFreq.put(word, wordFreq.get(word) + 1);
            } else {
                wordFreq.put(word, 1);
            }
        });
        return wordFreq;
    }

    private static List<Map.Entry<String, Integer>> sort(Map<String, Integer> wordFreq) {
        if (wordFreq == null /*!(wordFreq instanceof Map)*/ || wordFreq.isEmpty()) {
            return List.of();
        }

        return wordFreq.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        var fileName = args.length >= 1 ? args[0] : "data/swe262p/pride-and-prejudice.txt";
        var wordFreq = sort(frequencies(removeStopWords(extractWords(fileName))));

        wordFreq.subList(0, 25)
                .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
    }
}
