package edu.uci.swe262p_progstyles.week7.nineteen.src.app;

import edu.uci.swe262p_progstyles.week7.nineteen.src.framework.ITermFreqApp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-14.
 */
public class App1 implements ITermFreqApp {
    @Override
    public List<String> extractWords(String pathString) {
        final var words = new ArrayList<String>();
        try {
            final var stopWords = new HashSet<String>();
            Files.lines(Path.of("data/swe262p/stop_words.txt"))
                    .forEach(line -> Collections.addAll(stopWords, line.split(",")));
            stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));

            Files.lines(Path.of(pathString))
                    .forEach(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                            .map(String::toLowerCase)
                            .filter(word -> !stopWords.contains(word) && word.length() > 1)
                            .forEach(words::add));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return words;
    }

    @Override
    public List<Map.Entry<String, Integer>> top25(List<String> words) {
        final var map = new HashMap<String, Integer>();
        words.forEach(word -> map.put(word, map.containsKey(word) ? map.get(word) + 1 : 1));
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(Collectors.toList())
                .subList(0, 25);
    }
}
