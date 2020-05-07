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
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/22-tantrum">tantrum</a>
 */
public class TwentyOne {

    private static List<String> extractWords(String pathToFile) throws IOException {
        assert pathToFile instanceof String : "I need a string!";
        assert pathToFile != null : "I need a non-empty string!";

        Stream<String> stringData;
        try {
            stringData = Files.lines(Path.of(pathToFile));
        } catch (IOException e) {
            System.err.println("I/O error when opening " + pathToFile + ": " + e.getMessage());
            throw e;
        }

        final List<String> wordList = new ArrayList<>();
        stringData.forEach(line ->
                Arrays.stream(line.split("[\\W_]+"))
                        .filter(word -> word.length() > 1)
                        .map(String::toLowerCase)
                        .forEach(wordList::add));
        return wordList;
    }

    private static List<String> removeStopWords(List<String> wordList) throws IOException {
        assert wordList instanceof List : "I need a list!";

        final var stopWords = new HashSet<String>();
        try {
            Files.lines(Path.of("data/swe262p/stop_words.txt"))
                    .forEach(line -> Collections.addAll(stopWords, line.split(",")));
        } catch (IOException e) {
            System.err.println("I/O error when opening stop_words.txt: " + e.getMessage());
            throw e;
        }

        stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        return wordList.stream().filter(word -> !stopWords.contains(word)).collect(Collectors.toList());
    }

    private static Map<String, Integer> frequencies(List<String> wordList) {
        assert wordList instanceof List : "I need a list!";
        assert !wordList.isEmpty() : "I need a non-empty list!";

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
        assert wordFreq instanceof Map : "I need a map!";
        assert !wordFreq.isEmpty() : "I need a non-empty map!";

        try {
            return wordFreq.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Sorted threw " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            assert args.length >= 1 : "You idiot! I need an input file!";
            var wordFreq = sort(frequencies(removeStopWords(extractWords(args[0]))));

            assert wordFreq instanceof List : "OMG! This is not a list!";
            assert wordFreq.size() > 25 : "SRSLY? Less than 25 words!";
            wordFreq.subList(0, 25)
                    .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
        } catch (Exception e) {
            System.err.println("Something wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
