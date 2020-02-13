package edu.uci.swe241p.ex2_sorting_algorithms;

import java.time.LocalDateTime;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Sort
 */
abstract class Sort {

  abstract void sort(List<String> wordList);

  void swap(List<String> wordList, int a, int b) {
    var temp = wordList.get(a);
    wordList.set(a, wordList.get(b));
    wordList.set(b, temp);
  }

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

  public void run(List<String> wordList) {
    var className = this.getClass().getSimpleName();
    System.out.println(className + " test started.");

    // outputint to a txt file is a lot faster than console
    // make sure no colons in file name!
    var dateTime = LocalDateTime.now().toString().split("[.]")[0].replace(":", "-");

    // var sep = System.getProperty("file.separator");
    var fileDir = "./data/out/" + className + "/";

    // remove comments to check correctness
    // System.out.println(wordList);
    var start = System.nanoTime();
    this.sort(wordList);
    var end = System.nanoTime();
    var time = end - start;
    // System.out.println(wordList);

    var fileName = dateTime + "-" + className + "-summary.txt";
    var out = createPrintWriter(fileDir + "summary/" + fileName);
    out.println("************************");
    out.println(className + " test started at " + dateTime);
    out.println("************************");
    out.printf("Nanaseconds: %d\n", time);
    out.printf("Seconds: %.2f\n", (time / 1e9));
    out.println("************************");
    out.close();

    System.out.println(className + " test ended.");
    System.out.println();
  }
}