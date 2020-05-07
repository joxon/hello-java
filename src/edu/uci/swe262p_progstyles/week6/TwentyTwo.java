package edu.uci.swe262p_progstyles.week6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-06.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/23-passive-aggressive">passive-aggressive</a>
 */
public class TwentyTwo {

    private static List<String> extractWords(String pathToFile) throws IOException {
        assert pathToFile instanceof String : "I need a string! I quit!";
        assert pathToFile != null : "I need a non-empty string! I quit!";

        final var data = Files.lines(Path.of(pathToFile));
        final var wordList = new ArrayList<String>();
        data.forEach(line ->
                Arrays.stream(line.split("[\\W_]+"))
                        .filter(word -> word.length() > 1)
                        .map(String::toLowerCase)
                        .forEach(wordList::add));
        return wordList;
    }

    private static List<String> removeStopWords(List<String> wordList) throws IOException {
        assert wordList instanceof List : "I need a list! I quit!";

        final var stopWords = new HashSet<String>();
        Files.lines(Path.of("data/swe262p/stop_words.txt"))
                .forEach(line -> Collections.addAll(stopWords, line.split(",")));

        stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        return wordList.stream().filter(word -> !stopWords.contains(word)).collect(Collectors.toList());
    }

    private static Map<String, Integer> frequencies(List<String> wordList) {
        assert wordList instanceof List : "I need a list! I quit!";
        assert !wordList.isEmpty() : "I need a non-empty list! I quit!";

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
        assert wordFreq instanceof Map : "I need a map! I quit!";
        assert !wordFreq.isEmpty() : "I need a non-empty map! I quit!";

        return wordFreq.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        try {
            assert args.length >= 1 : "You idiot! I need an input file! I quit!";
            final var wordFreq = sort(frequencies(removeStopWords(extractWords(args[0]))));

            assert wordFreq.size() > 25 : "OMG! Less than 25 words! I QUIT!";
            wordFreq.subList(0, 25)
                    .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
        } catch (Exception e) {
            System.err.println("Something wrong: " + e.getMessage());
        }
    }
}
