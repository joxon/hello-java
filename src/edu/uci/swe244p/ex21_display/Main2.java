package edu.uci.swe244p.ex21_display;

public class Main2 {


  private static void nap(int millisecs) {
    try {
      Thread.sleep(millisecs);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    var d = new HighLevelDisplay();

    for (int i = 0; i < 20; i++) {
      d.addRow("AAAAAAAAAAAA  " + i);
      d.addRow("BBBBBBBBBBBB  " + i);
      nap(1);
      d.deleteRow(0);
      nap(1);
    }

  }
}
