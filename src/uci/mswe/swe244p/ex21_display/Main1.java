package uci.mswe.swe244p.ex21_display;

public class Main1 {

  private static void nap(int millisecs) {
    try {
      Thread.sleep(millisecs);
    } catch (InterruptedException e) {
      System.err.println(e.getMessage());
    }
  }


  public static void main(String[] args) {

    HardWareInterface hwd = new HardWareDisplay();

    // put A on row 5, col 2
    hwd.write(5, 2, 'A');

    // put B on row 8, col 11
    hwd.write(8, 11, 'B');

    // wait for 3000 ms
    nap(3000);

    // clear row 5, col 2, which is A
    hwd.write(5, 2, ' ');

  }
}
