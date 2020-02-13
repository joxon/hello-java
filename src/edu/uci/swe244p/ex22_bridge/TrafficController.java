package edu.uci.swe244p.ex22_bridge;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficController {
  private Queue<Integer> queue = new LinkedList<Integer>();

  private boolean bridgeIsBusy = false;

  // Red cars enter the bridge from left
  public synchronized void enterLeft() {
    queue.add(Car.REDCAR);
    while (bridgeIsBusy || queue.peek() == Car.BLUECAR) {
      try {
        wait(); // pause the thread until notified
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    bridgeIsBusy = true;
    queue.poll();
  }

  // Blue cars enter the bridge from right
  public synchronized void enterRight() {
    queue.add(Car.BLUECAR);
    while (bridgeIsBusy || queue.peek() == Car.REDCAR) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    bridgeIsBusy = true;
    queue.poll();
  }

  // Blue cars leave the bridge from left
  public synchronized void leaveLeft() {
    bridgeIsBusy = false;
    notifyAll();
  }

  // Red cars leave the bridge from right
  public synchronized void leaveRight() {
    bridgeIsBusy = false;
    notifyAll();
  }
}
