package uci.mswe.swe244p.ex22_bridge;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CarWindow extends JFrame {

  private static final long serialVersionUID = 1L;
  CarWorld world;
  JButton addLeft;
  JButton addRight;

  public CarWindow() {

    Container container = getContentPane();

    container.setLayout(new BorderLayout());
    world = new CarWorld();


    container.add("Center", world);
    addLeft = new JButton("Add Left");
    addRight = new JButton("Add Right");

    addLeft.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        world.addCar(Car.REDCAR);
      }
    });

    addRight.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        world.addCar(Car.BLUECAR);
      }
    });


    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    panel.add(addLeft);
    panel.add(addRight);
    container.add("South", panel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

}
