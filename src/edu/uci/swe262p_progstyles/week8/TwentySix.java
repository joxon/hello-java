package edu.uci.swe262p_progstyles.week8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Junxian Chen on 2020-05-19.
 *
 * @see <a href="https://github.com/crista/exercises-in-programming-style/tree/master/27-spreadsheet">spreadsheet</a>
 */

// have to suppress warnings for unchecked casts
// because we mean to manage type cast manually to make it look as dynamic as Python
@SuppressWarnings("unchecked")
public class TwentySix {

    private static class Column {
        // Made the data field an Object so we can make it dynamic like Python
        // However, we have to manage type casts very carefully
        Object data;
        Supplier<Object> formula;

        public Column(Object data, Supplier<Object> formula) {
            this.data = data;
            this.formula = formula;
        }
    }

    private static class Pair {
        // A container of the result list
        String word;
        Long count;

        public Pair(String word, Long count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public Long getCount() {
            return count;
        }
    }

    // Made columns final so they cannot be reassigned
    // But they are still mutable
    private static final Column allWords = new Column(null, null);
    private static final Column stopWords = new Column(null, null);

    private static final Column nonStopWords = new Column(null,
            () -> ((List<String>) allWords.data)
                    .stream()
                    .filter(word -> !((Set<String>) stopWords.data).contains(word))
                    // if word is not a stop word, then keep it
                    .collect(Collectors.toUnmodifiableList()));

    private static final Column uniqueWords = new Column(null,
            () -> List.copyOf( // convert the set to a list
                    Set.copyOf((List<String>) nonStopWords.data) // use Set to filter unique words
            ));

    private static final Column counts = new Column(null,
            () -> ((List<String>) uniqueWords.data) // loop each unique word
                    .stream() // DONT use parallelStream here because it has to be in order
                    .map(uniqueWord -> ((List<String>) nonStopWords.data) // map the word to its count
                            // .stream() // slow
                            .parallelStream() // faster
                            .filter(nonStopWord -> nonStopWord.equals(uniqueWord)) // maybe slow
                            .count()) // get the count of the word
                    .collect(Collectors.toUnmodifiableList()));

    private static final Column sortedData = new Column(null,
            () -> IntStream
                    .range(0, Math.min(((List<String>) uniqueWords.data).size(),
                            ((List<Long>) counts.data).size())
                    ) // i: 0 - smaller size
                    .mapToObj(i -> new Pair(((List<String>) uniqueWords.data).get(i),
                            ((List<Long>) counts.data).get(i))
                    ) // zip two lists into a list of Pair
                    .sorted(Comparator.comparingLong(Pair::getCount).reversed())
                    // sort it reversely
                    .collect(Collectors.toUnmodifiableList()));

    // The entire spreadsheet
    private static final List<Column> allColumns = List.of(allWords, stopWords, nonStopWords, uniqueWords, counts, sortedData);

    //
    // The active procedure over the columns of data.
    // Call this every time the input data changes, or periodically.
    //
    private static void update() {
        // Apply the formula in each column
        for (var column : allColumns) {
            if (column.formula != null) {
                column.data = column.formula.get();
            }
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

    public static void main(String[] args) throws IOException {
        validateArguments(args);

        // Load the fixed data into the first 2 columns
        allWords.data = new ArrayList<String>();
        Files.lines(Path.of(args[0]))
                .forEach(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                        .map(String::toLowerCase)
                        .filter(word -> word.length() > 1)
                        .forEach(((List<String>) allWords.data)::add));

        stopWords.data = new HashSet<String>();
        Files.lines(Path.of("data/swe262p/stop_words.txt"))
                .forEach(line -> Collections.addAll(((Set<String>) stopWords.data), line.split(",")));

        // Update the columns with formulas
        update();

        ((List<Pair>) sortedData.data).subList(0, 25)
                .forEach(pair -> System.out.println(pair.word + "  -  " + pair.count));

        // Make the example program interactive by
        // allowing the user to enter new file names
        // that are then added to the data space,
        // the columns updated, and the top 25 words displayed again.
        final var scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input a file path to add to the data space (Use 'q' to quit):");
            final var input = scanner.nextLine();
            if (input.equals("q")) {
                break;
            }

            // add new words to the data space
            try {
                Files.lines(Path.of(input))
                        .forEach(line -> Arrays.stream(line.split("[^a-zA-Z]+"))
                                .map(String::toLowerCase)
                                .filter(word -> word.length() > 1)
                                .forEach(((List<String>) allWords.data)::add));
            } catch (Exception e) {
                System.out.println(String.format("%s does not exist.", input));
                continue;
            }

            // update the columns
            update();

            // display the top 25 words again
            ((List<Pair>) sortedData.data).subList(0, 25)
                    .forEach(pair -> System.out.println(pair.word + " - " + pair.count));
        }
        scanner.close();
    }
}
