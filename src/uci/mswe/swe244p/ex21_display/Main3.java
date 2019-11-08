package uci.mswe.swe244p.ex21_display;

import java.util.Random;

public class Main3 {

  private static void nap(int millisecs) {
    try {
      Thread.sleep(millisecs);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void addProc(HighLevelInterface d) {
    // Add a sequence of addRow operations with short random naps.
    for (int i = 0; i < 100; ++i) {
      d.addRow("row " + i);
      nap(new Random().nextInt(100));
    }
  }

  private static void deleteProc(HighLevelInterface d) {
    // Add a sequence of deletions of row 0 with short random naps.
    for (int i = 0; i < 100; ++i) {
      d.deleteRow(0);
      nap(new Random().nextInt(500));
    }
  }

  public static void main(String[] args) {
    final HighLevelInterface d = new HighLevelDisplay();

    new Thread() {
      public void run() {
        addProc(d);
      }
    }.start();


    new Thread() {
      public void run() {
        deleteProc(d);
      }
    }.start();

  }
}
