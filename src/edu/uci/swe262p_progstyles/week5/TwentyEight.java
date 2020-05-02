package edu.uci.swe262p_progstyles.week5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-05-01.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/29-actors">29-actors</a>
 */
public class TwentyEight {

    @SuppressWarnings("unused")
    private static abstract class ActiveManager extends Thread {

        private final String name = this.getClass().getSimpleName();

        // DONT USE THIS. Messages will be lost due to concurrency.
        //        private final Queue<Object[]> queue = new LinkedList<>();

        // USE THIS. LinkedBlockingQueue is thread-safe.
        private final BlockingQueue<Object[]> queue = new LinkedBlockingQueue<>();

        private boolean shouldStop = false;

        ActiveManager() {
            super();
            this.start();
        }

        @Override
        public void run() {
            while (!shouldStop) {
                Object[] message = queue.poll();
                if (message != null) {
//                    System.out.println(name + " " + Arrays.toString(message));
                    this.dispatch(message);
                    if (message[0].equals("die")) {
                        shouldStop = true;
                    }
                }
            }
        }

        abstract void dispatch(Object[] message);

        public void addMessage(Object[] message) {
            queue.add(message);
        }

        public void end() {
            this.shouldStop = true;
        }

    }

    private static void send(ActiveManager manager, Object[] message) {
        manager.addMessage(message);
    }

    private static class MainController extends ActiveManager {

        private DataStorageManager dataStorageManager;

        @Override
        public void dispatch(Object[] message) {
            final String method = (String) message[0];
            if (method.equals("exec")) {
                this.exec(new Object[]{message[1]}); // ['exec', storage_manager]
            } else if (method.equals("display")) {
                this.display(new Object[]{message[1]});
            } else {
                System.err.println("MainController: unknown method " + method);
                System.exit(1);
            }
        }

        // start
        private void exec(Object[] message) {
            dataStorageManager = (DataStorageManager) message[0];
            send(dataStorageManager, new Object[]{"processWords", this});
        }

        // end
        @SuppressWarnings("unchecked")
        private void display(Object[] message) {
            final List<Map.Entry<String, Integer>> descendingList;
            descendingList = (List<Map.Entry<String, Integer>>) message[0];

            // print first 25 words
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < 25; ++i) {
                final Map.Entry<String, Integer> entry = descendingList.get(i);
                result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
            }
            System.out.println(result);

            end(); // the end
            send(dataStorageManager, new Object[]{"die"}); // "die" -> DataStorageManager -> StopWordsManager -> WordFrequencyMapManager
        }
    }

    private static class DataStorageManager extends ActiveManager {

        private Stream<String> lines;
        private StopWordsManager stopWordsManager;

        @Override
        public void dispatch(Object[] message) {
            final String method = (String) message[0];
            switch (method) {
                case "init":
                    this.init(new Object[]{message[1], message[2]}); // ['init', sys.argv[1], stop_word_manager]
                    break;
                case "processWords":
                    this.processWords(new Object[]{message[1]}); // {"processWords", controller}
                    break;
                case "die":
                default: // forwarding
                    send(stopWordsManager, message);
                    break;
            }
        }

        // called by main
        private void init(Object[] message) {
            stopWordsManager = (StopWordsManager) message[1];

            String spath = (String) message[0];
            try {
                lines = Files.lines(Path.of(spath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // called by MainController
        private void processWords(Object[] message) {
            MainController controller = (MainController) message[0];

            // start counting
            lines.forEach(line -> {
                String[] words = line.split("[^a-zA-Z]+");
                for (String word : words) {
                    String w = word.toLowerCase();
                    send(stopWordsManager, new Object[]{"filter", w});
                }
            });

            send(stopWordsManager, new Object[]{"top25", controller});
        }
    }

    private static class StopWordsManager extends ActiveManager {

        private final Set<String> stopWords = new HashSet<>();
        private WordFrequencyMapManager wordFrequencyMapManager;

        @Override
        public void dispatch(Object[] message) {
            final String method = (String) message[0];
            switch (method) {
                case "init":
                    this.init(new Object[]{message[1]}); // ['init', word_freq_manager]
                    break;
                case "filter":
                    this.filter(new Object[]{message[1]}); // {"filter", w}
                    break;
                case "die":
                case "top25":
                default: // forwarding
                    send(wordFrequencyMapManager, message);
                    break;
            }
        }

        // called by main
        private void init(Object[] message) {
            wordFrequencyMapManager = (WordFrequencyMapManager) message[0];

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

        // called by DataStorageManager
        private void filter(Object[] message) {
            String word = (String) message[0];
            if (!stopWords.contains(word) && word.length() > 1) {
                send(wordFrequencyMapManager, new Object[]{"increment", word});
            }
        }
    }

    private static class WordFrequencyMapManager extends ActiveManager {

        private final HashMap<String, Integer> frequencyMap = new HashMap<>();

        @Override
        public void dispatch(Object[] message) {
            final String method = (String) message[0];
            switch (method) {
                case "increment":
                    this.increment(new Object[]{message[1]}); // ["increment", word]
                    break;
                case "top25":
                    this.top25(new Object[]{message[1]}); // controller
                    break;
                case "die": // the end
                    break;
                default:
                    System.err.println("WordFrequencyMapManager: unknown method " + method);
                    System.exit(1);
                    break;
            }
        }

        // called by StopWordsManager
        private void increment(Object[] message) {
            String word = (String) message[0];
            if (frequencyMap.containsKey(word)) {
                frequencyMap.put(word, frequencyMap.get(word) + 1);
            } else {
                frequencyMap.put(word, 1);
            }
        }

        private void top25(Object[] message) {
            MainController controller = (MainController) message[0];

            // sort results
            final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(frequencyMap.entrySet());
            descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            send(controller, new Object[]{"display", descendingList.subList(0, 25)});
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

    public static void main(String[] args) throws Exception {
        validateArguments(args);

        WordFrequencyMapManager wordFrequencyMapManager = new WordFrequencyMapManager();

        StopWordsManager stopWordsManager = new StopWordsManager();
        send(stopWordsManager, new Object[]{"init", wordFrequencyMapManager});

        DataStorageManager dataStorageManager = new DataStorageManager();
        send(dataStorageManager, new Object[]{"init", args[0], stopWordsManager});

        MainController controller = new MainController();
        send(controller, new Object[]{"exec", dataStorageManager});

        controller.join();
        dataStorageManager.join();
        stopWordsManager.join();
        wordFrequencyMapManager.join();
    }
}
