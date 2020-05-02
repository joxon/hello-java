package edu.uci.swe262p_progstyles.week5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-02.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/30-dataspaces">30-dataspaces</a>
 */
public class TwentyNine {

    private static final BlockingQueue<String> wordSpace = new LinkedBlockingDeque<>();
    private static final BlockingQueue<HashMap<String, Integer>> freqSpace = new LinkedBlockingDeque<>();
    private static final Set<String> stopWords = new HashSet<>();

    static {
        // load stop words
        try {
            Files.lines(Path.of("data/swe262p/stop_words.txt"))
                    .forEach(line -> Collections.addAll(stopWords, line.split(",")));
        } catch (IOException e) {
            System.err.println("Error reading stop_words.txt");
            System.exit(1);
        }
    }

    private static final Runnable processWords = () -> {
        final HashMap<String, Integer> map = new HashMap<>();

        while (!wordSpace.isEmpty()) {
            String word;
            try {
                word = wordSpace.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                break;
            }
            if (!stopWords.contains(word)) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }

        try {
            freqSpace.put(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

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

    public static void main(String[] args) throws IOException {
        validateArguments(args);

        // this thread populates the word space
        Files.lines(Path.of(args[0]))
                .forEach(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                        .filter(word -> word.length() > 1)
                        .map(String::toLowerCase)
                        .forEach(wordSpace::add));

        // create the workers and launch them at their jobs
        final int WORKER_COUNT = 5;
        List<Thread> workers = new ArrayList<>(WORKER_COUNT);
        for (int i = 0; i < WORKER_COUNT; i++) {
            workers.add(new Thread(processWords));
        }
        workers.forEach(Thread::start);

        // wait for the workers to finish
        workers.forEach(worker -> {
            try {
                worker.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // merge the partial frequency results
        final Map<String, Integer> map = new HashMap<>();
        while (!freqSpace.isEmpty()) {
            final Map<String, Integer> subMap = freqSpace.poll();
            subMap.forEach((key, value) -> {
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + value);
                } else {
                    map.put(key, value);
                }
            });
        }

        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .collect(Collectors.toList())
                .subList(0, 25)
                .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
    }
}
