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

    public static void main(String[] args) throws IOException {
        // Load the data into the first 2 columns
        allWords.data = new ArrayList<String>();

        stopWords.data = Files.lines(Path.of("data/swe262p/stop_words.txt"))
                .flatMap(line -> Arrays.stream(line.split(",")))
                .collect(Collectors.toUnmodifiableSet());

        // Make the example program interactive by
        // allowing the user to enter new file names
        // that are then added to the data space,
        // the columns updated, and the top 25 words displayed again.
        final var allWordsData = (List<String>) allWords.data;
        final var scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input a file path to add to the data space:");
            System.out.println("(You can try providing ‘data/swe262p/pride-and-prejudice.txt’ twice)");
            System.out.println("(Use 'q' to quit)");
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
                                .forEach(allWordsData::add));
            } catch (Exception e) {
                System.out.println(String.format("%s does not exist.", input));
                continue;
            }

            // Update the columns with formulas
            update();

            // display the top 25 words again
            var sorted = ((List<Pair>) sortedData.data);
            sorted.subList(0, Math.min(25, sorted.size()))
                    .forEach(pair -> System.out.println(pair.word + " - " + pair.count));
        }
        scanner.close();
    }
}
