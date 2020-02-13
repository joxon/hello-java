package edu.uci.swe244p.ex22_bridge;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficControllerNoSync {
  private Queue<Integer> queue = new LinkedList<Integer>();

  private boolean bridgeIsBusy = false;

  // Red cars enter the bridge from left
  public void enterLeft() {
    queue.add(Car.REDCAR);
    while (bridgeIsBusy || queue.peek() == Car.BLUECAR) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
    bridgeIsBusy = true;
    queue.poll();
  }

  // Blue cars enter the bridge from right
  public void enterRight() {
    queue.add(Car.BLUECAR);
    while (bridgeIsBusy || queue.peek() == Car.REDCAR) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
      }
    }
    bridgeIsBusy = true;
    queue.poll();
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
