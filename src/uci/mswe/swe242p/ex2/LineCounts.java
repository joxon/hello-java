package uci.mswe.swe242p.ex2;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * LineCounts
 */
public class LineCounts {

  public static void main(String[] args) {
    final var fileCounts = args.length;
    if (fileCounts == 0) {
      System.err.println("Please provide one or more file paths.");
      return;
    }

    var fileIndex = 0;
    while (fileIndex < fileCounts) {
      System.out.println("[" + fileIndex + "]");

      var fileName = args[fileIndex];
      try {
        System.out.println(fileName + ": " //
            + LineCountsMethods.countLinesWithBufferedInputStreamBetter(fileName) //
            + " line(s)");
      } catch (FileNotFoundException e) {
        System.err.println("Cannot find " + fileName);
      } catch (IOException e) {
        System.err.println("An IOException occured when processing " + fileName);
      }

      ++fileIndex;
    }
  }

}