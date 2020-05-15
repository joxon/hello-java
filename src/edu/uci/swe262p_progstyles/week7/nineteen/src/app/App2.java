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
public class App2 implements ITermFreqApp {
    @Override
    public List<String> extractWords(String pathString) {
        final String PATH_STOP_WORDS = "data/swe262p/stop_words.txt";
        final Set<String> stopWords = new HashSet<>();
        try {
            final byte[] bytes = Files.readAllBytes(Path.of(PATH_STOP_WORDS));
            stopWords.addAll(Arrays.asList((new String(bytes).split(","))));
            stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        } catch (IOException e) {
            System.err.println("Error reading stop_words.txt");
            System.exit(1);
        }

        final List<String> wordList = new ArrayList<>();
        try {
            List<String> lines = Files.lines(Path.of(pathString)).collect(Collectors.toList());
            for (String line : lines) {
                String[] words = line.split("[^a-zA-Z]+");
                for (String word : words) {
                    String w = word.toLowerCase();
                    if (!stopWords.contains(w) && w.length() > 1) {
                        wordList.add(w);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return wordList;
    }

    @Override
    public List<Map.Entry<String, Integer>> top25(List<String> words) {
        final Map<String, Integer> map = new HashMap<>();
        words.forEach(word -> {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        });

        final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(map.entrySet());
        descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return descendingList.subList(0, 25);
    }
}
