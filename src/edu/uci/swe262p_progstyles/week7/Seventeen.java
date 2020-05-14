package edu.uci.swe262p_progstyles.week7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-13.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/14-abstract-things">abstract-things</a>
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/18-reflective">reflective</a>
 */
@SuppressWarnings("all")
public class Seventeen {

    private interface IDataStorageManager {
        List<String> getWords();
    }

    private static class DataStorageManager implements IDataStorageManager {

        private final List<String> words = new ArrayList<>();

        public DataStorageManager(String pathString) {
            try {
                Files.lines(Path.of(pathString))
                        .forEach(line -> Arrays.stream(line.split("[\\W_]+"))
                                .filter(word -> word.length() > 1)
                                .map(String::toLowerCase)
                                .forEach(words::add));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<String> getWords() {
            return words;
        }
    }

    private interface IStopWordManager {
        boolean isStopWord(String w);
    }

    private static class StopWordManager implements IStopWordManager {

        private final Set<String> stopWords = new HashSet<>();

        public StopWordManager() {
            try {
                Files.lines(Path.of("data/swe262p/stop_words.txt"))
                        .forEach(line -> Collections.addAll(stopWords, line.split(",")));
                stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
            } catch (IOException e) {
                System.err.println("Error reading stop_words.txt");
                System.exit(1);
            }
        }

        @Override
        public boolean isStopWord(String w) {
            return stopWords.contains(w);
        }
    }

    private interface IWordFrequencyManager {
        void increment(String w);

        List<Map.Entry<String, Integer>> getSortedList();
    }

    private static class WordFrequencyManager implements IWordFrequencyManager {

        private final HashMap<String, Integer> frequencyMap = new HashMap<>();

        @Override
        public void increment(String w) {
            if (frequencyMap.containsKey(w)) {
                frequencyMap.put(w, frequencyMap.get(w) + 1);
            } else {
                frequencyMap.put(w, 1);
            }
        }

        @Override
        public List<Map.Entry<String, Integer>> getSortedList() {
            return frequencyMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                    .collect(Collectors.toList());
        }
    }

    private static class WordFrequencyController {

        private final Class<IDataStorageManager> classDataStorageManager;
        private final Class<IStopWordManager> classStopWordManager;
        private final Class<IWordFrequencyManager> classWordFrequencyManager;

        private final IDataStorageManager dataStorageManager;
        private final IStopWordManager stopWordManager;
        private final IWordFrequencyManager wordFrequencyManager;

        public WordFrequencyController(String pathString) throws Exception {
            classDataStorageManager = (Class<IDataStorageManager>) Class.forName("edu.uci.swe262p_progstyles.week7.Seventeen$DataStorageManager");
            classStopWordManager = (Class<IStopWordManager>) Class.forName("edu.uci.swe262p_progstyles.week7.Seventeen$StopWordManager");
            classWordFrequencyManager = (Class<IWordFrequencyManager>) Class.forName("edu.uci.swe262p_progstyles.week7.Seventeen$WordFrequencyManager");

            dataStorageManager = classDataStorageManager.getDeclaredConstructor(String.class).newInstance(pathString);
            stopWordManager = classStopWordManager.getDeclaredConstructor().newInstance();
            wordFrequencyManager = classWordFrequencyManager.getDeclaredConstructor().newInstance();
        }

        public void run() throws Exception {
            for (var word : (List<String>) classDataStorageManager.getMethod("getWords").invoke(dataStorageManager)) {
                if (!((boolean) classStopWordManager.getMethod("isStopWord", String.class).invoke(stopWordManager, word))) {
                    classWordFrequencyManager.getMethod("increment", String.class).invoke(wordFrequencyManager, word);
                }
            }

            var wordFreqs = (List<Map.Entry<String, Integer>>) classWordFrequencyManager.getMethod("getSortedList").invoke(wordFrequencyManager);
            wordFreqs.subList(0, 25)
                    .forEach(entry -> System.out.println(entry.getKey() + "  -  " + entry.getValue()));
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

    private static void printClassInfo(String className) {
        var clazz = (Class) null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.err.println("Class '" + className + "' not found.");
            return;
        }

        System.out.println("=== Fields ===");
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> System.out.println("Name: " + field.getName() + "\nType: " + field.getType()));

        System.out.println("=== Methods ===");
        Arrays.stream(clazz.getDeclaredMethods()).
                forEach(method -> System.out.println("Name: " + method.getName() + "\nReturn Type: " + method.getReturnType()));

        System.out.println("=== Superclasses ===");
        var superClass = clazz.getSuperclass();
        while (superClass != null) {
            System.out.println("Name: " + superClass.getName());
            superClass = superClass.getSuperclass();
        }

        System.out.println("=== Implemented Interfaces ===");
        Arrays.stream(clazz.getInterfaces())
                .forEach(face -> System.out.println("Name: " + face.getName()));
    }

    public static void main(String[] args) throws Exception {
        validateArguments(args);
        new WordFrequencyController(args[0]).run();

        System.out.println("Please input a class name(\n" +
                "edu.uci.swe262p_progstyles.week7.Seventeen\n" +
                "edu.uci.swe262p_progstyles.week7.Seventeen$DataStorageManager\n" +
                "java.util.List\n" +
                "etc.):");
        printClassInfo(new Scanner(System.in).nextLine());
    }

}