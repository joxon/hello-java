package uci.mswe.swe244p.ex22_bridge;

public class Main {

  private static void nap(int ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
    }
  }

  public static void main(String[] a) {
    final CarWindow window = new CarWindow();

    window.pack();
    window.setVisible(true);

    new Thread(new Runnable() {
      public void run() {
        while (true) {
          nap(25);
          window.repaint();
        }
      }
    }).start();
  }

}
