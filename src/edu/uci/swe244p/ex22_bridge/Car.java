package edu.uci.swe244p.ex22_bridge;

import java.awt.*;

public class Car implements Runnable {

  public static final int REDCAR = 0;
  public static final int BLUECAR = 1;

  private final static int bridgeY = 95;
  private final static int bridgeXLeft = 210;
  private final static int bridgeXLeft2 = 290;
  private final static int bridgeXMid = 410;
  private final static int bridgeXRight2 = 530;
  private final static int bridgeXRight = 610;
  private final static int totalWidth = 900;
  private final static int initX[] = {-80, totalWidth};
  private final static int initY[] = {135, 55};
  private final static int outLeft = -200;
  private final static int outRight = totalWidth + 200;

  int carType;
  int xpos, ypos;
  Car inFront;
  Image image;
  TrafficController controller;

  public Car(int carType, Car inFront, Image image, TrafficController controller) {
    this.carType = carType;
    this.inFront = inFront;
    this.image = image;
    this.controller = controller;
    if (carType == REDCAR)
      xpos = inFront == null ? outRight : Math.min(initX[carType], inFront.getX() - 90);
    else
      xpos = inFront == null ? outLeft : Math.max(initX[carType], inFront.getX() + 90);
    ypos = initY[carType];
  }

  // ! modify this function
  public void move() {
    int xposOld = xpos;
    if (carType == REDCAR) {
      // Red cars come from the left to the right
      if (inFront.getX() - xpos > 100) {
        xpos += 4;
        if (xpos >= bridgeXLeft && xposOld < bridgeXLeft)
          controller.enterLeft(); // ! lock the bridge
        else if (xpos > bridgeXLeft && xpos < bridgeXMid) {
          if (ypos > bridgeY)
            ypos -= 2;
        } else if (xpos >= bridgeXRight2 && xpos < bridgeXRight) {
          if (ypos < initY[REDCAR])
            ypos += 2;
        } else if (xpos >= bridgeXRight && xposOld < bridgeXRight)
          controller.leaveRight(); // ! release the bridge
      }
    } else {
      // Blue cars come from the right to the left
      if (xpos - inFront.getX() > 100) {
        xpos -= 4;
        if (xpos <= bridgeXRight && xposOld > bridgeXRight)
          controller.enterRight(); // ! lock the bridge
        else if (xpos < bridgeXRight && xpos > bridgeXMid) {
          if (ypos < bridgeY)
            ypos += 2;
        } else if (xpos <= bridgeXLeft2 && xpos > bridgeXLeft) {
          if (ypos > initY[BLUECAR])
            ypos -= 2;
        } else if (xpos <= bridgeXLeft && xposOld > bridgeXLeft)
          controller.leaveLeft(); // ! release the bridge
      }
    }
  }



  public void run() {
    boolean outOfSight = carType == REDCAR ? xpos > totalWidth : xpos < -80;
    while (!outOfSight) {
      move();
      outOfSight = carType == REDCAR ? xpos > totalWidth : xpos < -80;
      try {
        Thread.sleep(30);
      } catch (InterruptedException e) {
      }
    }
    xpos = carType == REDCAR ? outRight : outLeft;
  }

  public int getX() {
    return xpos;
  }

  public void draw(Graphics g) {
    g.drawImage(image, xpos, ypos, null);
  }
}
