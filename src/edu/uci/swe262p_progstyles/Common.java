package edu.uci.swe262p_progstyles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-02.
 */
public class Common {


    public static void main(String[] args) throws IOException {
//        loadStopWords();
        final Set<String> stopWords = new HashSet<>();
        Files.lines(Path.of("data/swe262p/stop_words.txt"))
                .forEach(line -> Collections.addAll(stopWords, line.split(",")));
        
//        loadWords(args[0]);
        final List<String> words = new ArrayList<>();
        Files.lines(Path.of(args[0]))
                .forEach(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                        .map(String::toLowerCase)
                        .filter(word -> !stopWords.contains(word) && word.length() > 1)
                        .forEach(words::add));

//        count();
        final Map<String, Integer> map = new HashMap<>();
        words.forEach(word -> {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        });

//        sortAndPrint();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(Collectors.toList())
                .subList(0, 25)
                .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
    }

}
