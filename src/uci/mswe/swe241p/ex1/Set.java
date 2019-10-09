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

  private PrintWriter createPrintWriter(String filePath) {
    var fout = new File(filePath);
    // Java does not create folder for us
    fout.getParentFile().mkdirs();
    try {
      if (fout.createNewFile()) {
        System.out.println(filePath + " is created!");
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }

    try {
      return new PrintWriter(new FileWriter(fout));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void run() {
    var className = this.getClass().getSimpleName();
    System.out.println(className + " test started.");

    // outputint to a txt file is a lot faster than console
    // make sure no colons in file name!
    var dateTime = LocalDateTime.now().toString().split("[.]")[0].replace(":", "-");

    // var sep = System.getProperty("file.separator");
    var fileDir = "./data/out/" + className + "/";

    // read all words in the book and save them to the set
    var wordsInBook = 0;
    try (Scanner scanner = new Scanner(new File("./data/in/pride-and-prejudice.txt"))) {
      var fileName = dateTime + "-" + className + "-add.txt";
      var out = createPrintWriter(fileDir + "add/" + fileName);
      out.println("Index,FuncName,FuncReturns,NanoSeconds");

      while (scanner.hasNextLine()) {
        var line = scanner.nextLine();
        String[] words;

        words = line.split("[^\\w\\d_]");

        var start = System.nanoTime();
        for (var word : words) {
          if (word != null && word.length() > 0) {
            // var wordLowerCased = word.toLowerCase();
            // https://www.techiedelight.com/measure-elapsed-time-execution-time-java/
            var wordAdded = this.add(word);
            ++wordsInBook;
            if (wordsInBook % 100 == 0) {
              var end = System.nanoTime();
              var time = end - start;
              out.printf("%d,add(%s),%b,%d\n", wordsInBook, word, wordAdded, time);
              start = System.nanoTime();
            }
          }
        }
      }

      out.close();
      // scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    // read shuffled words and search them in the set
    var wordsShuffled = 0;
    var wordContainedCount = 0;
    try (Scanner scanner = new Scanner(new File("./data/in/words-shuffled.txt"))) {
      var fileName = dateTime + "-" + className + "-contains.txt";
      var out = createPrintWriter(fileDir + "contains/" + fileName);
      out.println("Index,FuncName,FuncReturns,NanoSeconds");

      while (scanner.hasNextLine()) {
        var word = scanner.nextLine();
        var start = System.nanoTime();
        var wordContained = this.contains(word);
        if (wordContained) {
          ++wordContainedCount;
        }
        var end = System.nanoTime();
        var time = end - start;
        out.printf("%d,contains(%s),%b,%d\n", ++wordsShuffled, word, wordContained, time);
      }

      out.close();
      // scanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return;
    }

    var fileName = dateTime + "-" + className + "-summary.txt";
    var out = createPrintWriter(fileDir + "summary/" + fileName);
    out.println("************************");
    out.println(className + " test started at " + dateTime);
    out.println("************************");
    out.println("Words in book = " + wordsInBook);
    out.println("Words unique or set size = " + this.size());
    out.println("************************");
    out.println("Words shuffled = " + wordsShuffled);
    out.println("Words contained in set = " + wordContainedCount);
    out.println("Words NOT contained = " + (wordsShuffled - wordContainedCount));
    out.println("************************");
    out.close();

    System.out.println(className + " test ended.");
    System.out.println();
  }
}