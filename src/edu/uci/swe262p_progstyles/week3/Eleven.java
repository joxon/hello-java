package edu.uci.swe262p_progstyles.week3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Junxian Chen on 2020-04-16.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/12-letterbox">letterbox</a>
 */

interface Responsive {
    abstract class BaseResponse {
        BaseResponse() {
        }
    }

    BaseResponse dispatch(String[] messages) throws Exception;
}

final class TextLinesManager implements Responsive {

    class Response extends Responsive.BaseResponse {
        Stream<String> lines;

        Response() {
        }

        Response(Stream<String> lines) {
            this.lines = lines;
        }

        public Stream<String> getLines() {
            return lines;
        }
    }

    private Stream<String> lines;

    @Override
    public Response dispatch(String[] messages) throws Exception {
        final String method = messages[0];
        if (method.equals("init")) {
            return this.init(messages[1]);
        } else if (method.equals("get")) {
            return this.get();
        } else {
            throw new Exception("LinesManager: unknown method " + method);
        }
    }

    private Response init(String spath) {
        try {
            lines = Files.lines(Path.of(spath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response();
    }

    private Response get() {
        return new Response(lines);
    }
}

final class StopWordsManager implements Responsive {

    class Response extends Responsive.BaseResponse {
        private boolean isStopWord;

        public Response() {
        }

        public Response(boolean b) {
            this.isStopWord = b;
        }

        public boolean isStopWord() {
            return isStopWord;
        }
    }

    private final Set<String> stopWords = new HashSet<>();

    @Override
    public Response dispatch(String[] messages) throws Exception {
        final String method = messages[0];
        if (method.equals("init")) {
            return this.init();
        } else if (method.equals("isStopWord")) {
            return this.isStopWord(messages[1]);
        } else {
            throw new Exception("StopWordManager: unknown method " + method);
        }
    }

    private Response init() {
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
        return new Response();
    }

    private Response isStopWord(String w) {
        return new Response(stopWords.contains(w));
    }
}

final class FrequencyMapManager implements Responsive {

    class Response extends Responsive.BaseResponse {
        private HashMap<String, Integer> frequencyMap;

        public Response() {
        }

        public Response(HashMap<String, Integer> frequencyMap) {
            this.frequencyMap = frequencyMap;
        }

        public HashMap<String, Integer> getFrequencyMap() {
            return frequencyMap;
        }
    }

    private final HashMap<String, Integer> frequencyMap = new HashMap<>();

    @Override
    public Response dispatch(String[] messages) throws Exception {
        final String method = messages[0];
        if (method.equals("get")) {
            return this.get();
        } else if (method.equals("increment")) {
            return this.increment(messages[1]);
        } else {
            throw new Exception("FrequencyMapManager: unknown method " + method);
        }
    }

    private Response get() {
        return new Response(frequencyMap);
    }

    private Response increment(String w) {
        if (frequencyMap.containsKey(w)) {
            frequencyMap.put(w, frequencyMap.get(w) + 1);
        } else {
            frequencyMap.put(w, 1);
        }
        return new Response();
    }
}

final class MainController {

    private TextLinesManager textLinesManager;
    private StopWordsManager stopWordsManager;
    private FrequencyMapManager frequencyMapManager;

    public void dispatch(String[] messages) throws Exception {
        final String method = messages[0];
        if (method.equals("init")) {
            init(messages[1]);
        } else if (method.equals("run")) {
            run();
        } else {
            throw new Exception("MainController: unknown method " + method);
        }
    }

    private void init(String spath) throws Exception {
        this.textLinesManager = new TextLinesManager();
        textLinesManager.dispatch(new String[]{"init", spath});

        this.stopWordsManager = new StopWordsManager();
        stopWordsManager.dispatch(new String[]{"init"});

        this.frequencyMapManager = new FrequencyMapManager();
    }

    private void run() throws Exception {
        // start counting
        textLinesManager.dispatch(new String[]{"get"}).getLines().forEach(line -> {
            try {
                String[] words = line.split("[^a-zA-Z]+");
                for (String word : words) {
                    String w = word.toLowerCase();
                    if (!stopWordsManager.dispatch(new String[]{"isStopWord", w}).isStopWord() && w.length() > 1) {
                        frequencyMapManager.dispatch(new String[]{"increment", w});
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // sort results
        final List<Map.Entry<String, Integer>> descendingList = new ArrayList<>(
                frequencyMapManager.dispatch(new String[]{"get"}).getFrequencyMap().entrySet());
        descendingList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // print first 25 words
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < 25; ++i) {
            final Map.Entry<String, Integer> entry = descendingList.get(i);
            result.append(entry.getKey()).append("  -  ").append(entry.getValue()).append("\n");
        }
        System.out.println(result);
    }
}

public class Eleven {

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

    private void run(String[] args) throws Exception {
        MainController controller = new MainController();
        controller.dispatch(new String[]{"init", args[0]});
        controller.dispatch(new String[]{"run"});
    }

    public static void main(String[] args) throws Exception {
        validateArguments(args);
        new Eleven().run(args);
    }

}