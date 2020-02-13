package edu.uci.swe244p.ex22_bridge;

import java.awt.*;
import java.util.*;
import javax.swing.*;

class CarWorld extends JPanel {

  private static final long serialVersionUID = 1L;
  Image bridge;
  Image redCar;
  Image blueCar;

  TrafficController controller;

  ArrayList<Car> blueCars = new ArrayList<Car>();
  ArrayList<Car> redCars = new ArrayList<Car>();

  public CarWorld() {
    // ! TrafficController
    controller = new TrafficController();
    MediaTracker tracker = new MediaTracker(this);
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    redCar = toolkit.getImage("src/edu/uci/swe244p/ex22_bridge/image/redcar.gif");
    tracker.addImage(redCar, 0);
    blueCar = toolkit.getImage("src/edu/uci/swe244p/ex22_bridge/image/bluecar.gif");
    tracker.addImage(blueCar, 1);
    bridge = toolkit.getImage("src/edu/uci/swe244p/ex22_bridge/image/bridge1.gif");
    tracker.addImage(bridge, 2);

    try {
      tracker.waitForID(0);
      tracker.waitForID(1);
      tracker.waitForID(2);
    } catch (java.lang.InterruptedException e) {
      System.out.println("Couldn't load one of the images");
    }

    redCars.add(new Car(Car.REDCAR, null, redCar, null));
    blueCars.add(new Car(Car.BLUECAR, null, blueCar, null));
    setPreferredSize(new Dimension(bridge.getWidth(null), bridge.getHeight(null)));
  }

  public void paintComponent(Graphics g) {
    g.drawImage(bridge, 0, 0, this);
    for (Car c : redCars)
      c.draw(g);
    for (Car c : blueCars)
      c.draw(g);
  }

  public void addCar(final int cartype) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Car c;
        if (cartype == Car.REDCAR) {
          c = new Car(cartype, redCars.get(redCars.size() - 1), redCar, controller);
          redCars.add(c);
        } else {
          c = new Car(cartype, blueCars.get(blueCars.size() - 1), blueCar, controller);
          blueCars.add(c);
        }
        new Thread(c).start();
      }
    });
  }

}
