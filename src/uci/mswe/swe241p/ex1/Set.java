package uci.mswe.swe241p.ex1;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Set: A set is a data structure that stores unique elements, with no
 * repetitions. In either Python or Java, implement sets of words.
 */
abstract class Set {
  /**
   * boolean add(String word): Takes a word as input and adds the word to the set
   * if the word is not already there. Returns true if the word was added and
   * returns false if the word is already present in the set.
   *
   * @param word String
   * @return true if the word was added and false if the word is already present
   *         in the set.
   */
  public abstract boolean add(String word);

  /**
   * boolean contains(String word): Takes a word as input and returns whether the
   * word is present in the set (true) or not (false).
   *
   * @param word String
   * @return whether the word is present in the set (true) or not (false).
   */
  public abstract boolean contains(String word);

  /**
   * int size(): Returns the size of the set.
   *
   * @return the size of the set.
   */
  public abstract int size();

  private final boolean useRegexToSplit = true;

  public void run() {
    var className = this.getClass().getSimpleName();
    System.out.println(className + " test started.");
    // outputint to a txt file is a lot faster than console
    // make sure no colons in file name!
    var dateTime = LocalDateTime.now().toString().replace(":", "-");
    // var sep = System.getProperty("file.separator");
    var fpath = "./data/out/" + className + "-" + dateTime + ".txt";
    var fout = new File(fpath);
    try {
      if (fout.createNewFile()) {
        System.out.println(fpath + " is created!");
      } else {
        System.out.println("File already exists.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    try (PrintWriter out = new PrintWriter(new FileWriter(fout))) {
      // read all words in the book and save them to the set
      try (Scanner scanner = new Scanner(new File("./data/in/pride-and-prejudice.txt"))) {
        var total = 0;
        while (scanner.hasNextLine()) {
          var line = scanner.nextLine();
          String[] words;

          if (useRegexToSplit) {
            words = line.split("[^a-zA-Z0-9]");
          } else {
            words = splitLineByChar(line);
          }

          for (var word : words) {
            if (word != null && word.length() > 0) {
              // var wordLowerCased = word.toLowerCase();
              // https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
              var start = System.nanoTime();
              var wordAdded = this.add(word);
              var end = System.nanoTime();
              var time = end - start;
              out.printf("%d. add(%s): %b, %d ns\n", ++total, word, wordAdded, time);
            }
          }
        }
        // scanner.close();
        out.println("************************");
        out.println("Total word count (book) = " + total);
        out.println("Set size = " + this.size());
        out.println("************************");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return;
      }
      // read shuffled words and search them in the set
      try (Scanner scanner = new Scanner(new File("./data/in/words-shuffled.txt"))) {
        var total = 0;
        var wordContainedCount = 0;
        while (scanner.hasNextLine()) {
          var word = scanner.nextLine();
          var start = System.nanoTime();
          var wordContained = this.contains(word);
          if (wordContained) {
            ++wordContainedCount;
          }
          var end = System.nanoTime();
          var time = end - start;
          out.printf("%d. contains(%s): %b, %d ns\n", ++total, word, wordContained, time);
        }
        // scanner.close();
        out.println("************************");
        out.println("Total word count (shuffled) = " + total);
        out.println("Total word contained in set = " + wordContainedCount);
        out.println("Total word NOT contained = " + (total - wordContainedCount));
        out.println("************************");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return;
      }

    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    System.out.println(className + " test ended.");
  }

  private String[] splitLineByChar(String line) {
    int len = line.length();
    String[] words = new String[len];
    String word = "";
    int wordCount = 0;
    // Add every word of the line except the last one
    for (int i = 0; i < len; ++i) {
      char ch = line.charAt(i);
      boolean isValidChar = Character.isDigit(ch) || Character.isAlphabetic(ch);
      // Construct the word
      if (isValidChar) {
        word += ch;
      }
      // If char not valid, then add it to the set
      else if (word.length() > 0) {
        words[wordCount++] = word;
        word = "";
      }
    }
    // Add final word of the line
    if (word.length() > 0) {
      words[wordCount++] = word;
      word = "";
    }
    return words;
  }
}