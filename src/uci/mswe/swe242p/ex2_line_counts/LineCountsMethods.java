package uci.mswe.swe242p.ex2_line_counts;

import java.io.*;
import java.nio.file.*;

/**
 * LineCountsMethods
 *
 * @see <a href=
 *
 *      "https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java">
 *      number-of-lines-in-a-file-in-java</a>
 * @see <a href=
 *
 *      "https://stackoverflow.com/questions/1277880/how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way">
 *      how-can-i-get-the-count-of-line-in-a-file-in-an-efficient-way</a>
 */
public class LineCountsMethods {

  public static void println() {
    System.out.println();
  }

  public static <T> void println(T arg) {
    System.out.println(arg);
  }

  public static void main(String[] args) {
    // In the root folder, try:
    // javac LineCountsMethods.java && java -classpath ./src
    // uci.mswe.swe242p.ex2_line_counts.LineCountsMethods
    try {
      println(countLinesWithNIOFiles("./data/in/one-line-0.txt")); // 0
      println(countLinesWithNIOFiles("./data/in/one-line-1.txt")); // 1
      println(countLinesWithNIOFiles("./data/in/one-line-2.txt")); // 1
      println(countLinesWithNIOFiles("./data/in/pride-and-prejudice.txt")); // 13426
      println(countLinesWithNIOFiles("./data/in/words-shuffled.txt")); // 6744
      println();

      println(countLinesWithLineNumberReader("./data/in/one-line-0.txt")); // 0
      println(countLinesWithLineNumberReader("./data/in/one-line-1.txt")); // ! 1
      println(countLinesWithLineNumberReader("./data/in/one-line-2.txt")); // 1
      println(countLinesWithLineNumberReader("./data/in/pride-and-prejudice.txt")); // 13426
      println(countLinesWithLineNumberReader("./data/in/words-shuffled.txt")); // 6744
      println();

      println(countLinesWithBufferedInputStreamBetter("./data/in/one-line-0.txt")); // 0
      println(countLinesWithBufferedInputStreamBetter("./data/in/one-line-1.txt")); // 1
      println(countLinesWithBufferedInputStreamBetter("./data/in/one-line-2.txt")); // 1
      println(countLinesWithBufferedInputStreamBetter("./data/in/pride-and-prejudice.txt")); // 13426
      println(countLinesWithBufferedInputStreamBetter("./data/in/words-shuffled.txt")); // 6744
      println();

      println(countLinesWithBufferedInputStream("./data/in/one-line-0.txt")); // 0
      println(countLinesWithBufferedInputStream("./data/in/one-line-1.txt")); // 1
      println(countLinesWithBufferedInputStream("./data/in/one-line-2.txt")); // 1
      println(countLinesWithBufferedInputStream("./data/in/pride-and-prejudice.txt")); // 13426
      println(countLinesWithBufferedInputStream("./data/in/words-shuffled.txt")); // 6744
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static long countLinesWithNIOFiles(String filename) throws IOException {
    return Files.lines(Paths.get(filename)).count();
  }

  public static int countLinesWithLineNumberReader(String filename)
      throws FileNotFoundException, IOException {
    var lnr = new LineNumberReader(// Readers deal with characters
        new FileReader(filename));

    // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read
    // the entire file
    while (lnr.skip(Long.MAX_VALUE) > 0);

    var lineCounts = lnr.getLineNumber();
    lnr.close();
    return lineCounts;
  }

  public static int countLinesWithBufferedInputStreamBetter(String filename)
      throws FileNotFoundException, IOException {
    final int BUFFER_SIZE = 1024;
    int lineCounts = 0;

    var bis = new BufferedInputStream(// InputStreams deal with bytes
        new FileInputStream(filename));
    byte[] bytes = new byte[BUFFER_SIZE];

    int byteCounts = bis.read(bytes);
    if (byteCounts == -1) {
      // empty text file: 0 lines;
      bis.close();
      return 0;
    }

    // make it easy for the optimizer to tune this loop
    while (byteCounts == BUFFER_SIZE) {
      for (int i = 0; i < BUFFER_SIZE; ++i) {
        if (bytes[i] == '\n') {
          ++lineCounts;
        }
      }
      byteCounts = bis.read(bytes);
    }

    // count remaining characters
    if (byteCounts != -1) {
      for (int i = 0; i < byteCounts; ++i) {
        if (bytes[i] == '\n') {
          ++lineCounts;
        }
      }
    }

    /**
     * empty text file: 0 lines;
     *
     * text file with one line but no '\n': 1 line;
     *
     * text file with one line and '\n', and the 2nd line is empty: 1 line;
     *
     * so it is not enough to just count '\n', we need to check the content after the last '\n'
     */
    if (bytes[byteCounts - 1] != '\n') {
      ++lineCounts;
    }

    bis.close();
    return lineCounts;
  }

  public static int countLinesWithBufferedInputStream(String filename)
      throws FileNotFoundException, IOException {
    final int BUFFER_SIZE = 1024;
    int lineCounts = 0;

    var bis = new BufferedInputStream(// InputStreams deal with bytes
        new FileInputStream(filename));
    byte[] bytes = new byte[BUFFER_SIZE];

    int byteCounts = bis.read(bytes);
    if (byteCounts == -1) {
      bis.close();
      return 0;
    }

    while (byteCounts != -1) {
      for (int i = 0; i < byteCounts; ++i) {
        if (bytes[i] == '\n') {
          ++lineCounts;
        }
      }
      if (byteCounts < BUFFER_SIZE) {
        // if we reach the end of the file,
        // then break to check if the last byte is '\n'
        break;
      } else {
        // else we continue to load the buffer
        byteCounts = bis.read(bytes);
      }
    }

    if (bytes[byteCounts - 1] != '\n') {
      ++lineCounts;
    }

    bis.close();
    return lineCounts;
  }

}
