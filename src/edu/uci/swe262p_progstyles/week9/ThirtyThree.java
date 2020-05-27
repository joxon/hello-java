package edu.uci.swe262p_progstyles.week9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Junxian Chen on 2020-05-26.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/34-restful">restful</a>
 */
public class ThirtyThree {

    // A wrapper class for requests
    private static class Request {
        String verb;
        String uri;
        List<String> args;

        public Request(String verb, String uri, List<String> args) {
            this.verb = verb;
            this.uri = uri;
            this.args = args;
        }

        public String getHandlerKey() {
            return verb + "_" + uri;
        }

        public static Request getDefault() {
            return new Request("get", "default", null);
        }
    }

    // A wrapper class for state representations and links
    private static class StateAndLinks {
        String stateRepresentation;
        Map<String, Request> links;

        public StateAndLinks(String stateRepresentation, Map<String, Request> links) {
            this.stateRepresentation = stateRepresentation;
            this.links = links;
        }

        public static StateAndLinks errorState() {
            return new StateAndLinks("Something wrong",
                    Map.of("", Request.getDefault()));
        }
    }

    // A consumer that throws exceptions
    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws Exception;
    }

    private static final Set<String> stopWords = new HashSet<>();

    static {
        try {
            stopWords.addAll(Arrays.asList(new String(Files.readAllBytes(Path.of("data/swe262p/stop_words.txt"))).split(",")));
            stopWords.addAll(Arrays.asList("abcdefghijklmnopqrstuvwxyz".split("")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // The database
    private static final Map<String, List<Map.Entry<String, Integer>>> data = new HashMap<>();

    // region Internal functions of the server-side application

    private static StateAndLinks getDefaultHandler(List<String> arguments) {
        return new StateAndLinks(
                "What would you like to do?\n" +
                        "1 - Quit\n" +
                        "2 - Upload file",
                Map.of("1", new Request("post", "execution", null),
                        "2", new Request("get", "file_form", null)));
    }

    private static StateAndLinks quitHandler(List<String> arguments) {
        System.out.println("Goodbye cruel world...");
        System.exit(0);
        // won't return
        return new StateAndLinks("", Map.of());
    }

    private static StateAndLinks getUploadHandler(List<String> arguments) {
        return new StateAndLinks("Name of file to upload?",
                Map.of("", new Request("post", "file", null)));
    }

    private static StateAndLinks postUploadHandler(List<String> arguments) {
        final var createData = (ThrowingConsumer<String>) (fileName) -> {
            if (data.containsKey(fileName)) {
                return;
            }
            // load text file into lines
            var list = Files.lines(Path.of(fileName))
                    // split line to words: Stream<String>
                    .flatMap(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                            // normalize words
                            .map(String::toLowerCase)
                            // ignore single letters
                            .filter(word -> word.length() > 1)
                            // ignore stop words
                            .filter(word -> !stopWords.contains(word)))
                    // wordFreqMap: Map<String, Integer>
                    .collect(Collectors.toMap(word -> word, count -> 1, Integer::sum))
                    // Set<Map.Entry<String, Integer>>
                    .entrySet()
                    // Stream<Map.Entry<String, Integer>>
                    .stream()
                    .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                    .collect(Collectors.toUnmodifiableList());
            data.put(fileName, list);
        };

        if (arguments == null) {
            return StateAndLinks.errorState();
        }
        var fileName = arguments.get(0);
        try {
            createData.accept(fileName);
        } catch (Exception e) {
            System.err.println("Unexpected error when creating data: " + e.getMessage());
            return StateAndLinks.errorState();
        }
        return getWordHandler(List.of(fileName, "0"));
    }

    private static StateAndLinks getWordHandler(List<String> arguments) {
        final var getWord = (BiFunction<String, Integer, Map.Entry<String, Integer>>)
                (fileName, wordIndex) ->
                        0 <= wordIndex && wordIndex < data.getOrDefault(fileName, List.of()).size() ?
                                data.get(fileName).get(wordIndex) :
                                Map.entry("no more words", 0);

        var fileName = arguments.get(0);
        var wordIndex = Integer.parseInt(arguments.get(1));
        if (wordIndex < 0) {
            wordIndex = 0;
        }
        var wordInfo = getWord.apply(fileName, wordIndex);
        return new StateAndLinks(
                String.format("\n#%d: %s - %d", wordIndex + 1, wordInfo.getKey(), wordInfo.getValue())
                        + "\n\nWhat would you like to do next?"
                        + "\n1 - Quit"
                        + "\n2 - Upload file"
                        + "\n3 - See next most-frequently occurring word"
                        + "\n4 - See previous most-frequently occurring word",
                Map.of("1", new Request("post", "execution", null),
                        "2", new Request("get", "file_form", null),
                        "3", new Request("get", "word", List.of(fileName, String.valueOf(wordIndex + 1))),
                        "4", new Request("get", "word", List.of(fileName, String.valueOf(wordIndex - 1)))
                )
        );
    }

    private static final Map<String, Function<List<String>, StateAndLinks>> handlers = Map.of(
            "post_execution", ThirtyThree::quitHandler,
            "get_default", ThirtyThree::getDefaultHandler,
            "get_file_form", ThirtyThree::getUploadHandler,
            "post_file", ThirtyThree::postUploadHandler,
            "get_word", ThirtyThree::getWordHandler
    );
    // endregion

    private static StateAndLinks handleRequest(Request request) {
        return handlers.containsKey(request.getHandlerKey()) ?
                handlers.get(request.getHandlerKey()).apply(request.args) :
                handlers.get("get_default").apply(request.args);
    }

    private static Request renderAndGetInput(StateAndLinks stateAndLinks) {
        final var state = stateAndLinks.stateRepresentation;
        final var links = stateAndLinks.links;

        System.out.println(state);
        System.out.flush();
        if (links.isEmpty()) {
            // no more next states
            return Request.getDefault();
        } else if (links.size() == 1) {
            // only one next state
            var request = links.values().iterator().next();
            if (request.verb.equals("post")) {
                // get "form" data
                var input = new Scanner(System.in).nextLine().trim();
                request.args = List.of(input);
            }
            return request;
        } else {
            // many next states
            var input = new Scanner(System.in).nextLine().trim();
            return links.containsKey(input) ?
                    links.get(input) :
                    Request.getDefault();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        StateAndLinks stateAndLinks;
        var request = Request.getDefault();
        while (true) {
            // server-side computation
            stateAndLinks = handleRequest(request);
            // client-side computation
            request = renderAndGetInput(stateAndLinks);
        }
    }
}
