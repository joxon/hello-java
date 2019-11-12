package uci.mswe.swe244p.ex22_bridge;

public class TrafficController {
  // TODO: use the simple monitor mechanisms in class Object?

  private boolean bridgeIsBusy = false;

  // Red cars enter the bridge from left
  public void enterLeft() {
    while (bridgeIsBusy) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
    bridgeIsBusy = true;
  }

  // Blue cars enter the bridge from right
  public void enterRight() {
    while (bridgeIsBusy) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
    bridgeIsBusy = true;
  }

  // Blue cars leave the bridge from left
  public void leaveLeft() {
    bridgeIsBusy = false;
  }

  // Red cars leave the bridge from right
  public void leaveRight() {
    bridgeIsBusy = false;
  }

}
