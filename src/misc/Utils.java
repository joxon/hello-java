package misc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utils
 */
public class Utils {

  public static PrintWriter createPrintWriter(String filePath) {
    var fout = new File(filePath);
    fout.getParentFile().mkdirs(); // Java does not create folder for us

    try {
      if (fout.createNewFile()) {
        System.out.println(filePath + " is created!");
      } else {
        System.out.println(filePath + " already exists.");
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
}