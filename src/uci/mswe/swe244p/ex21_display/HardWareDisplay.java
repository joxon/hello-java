package uci.mswe.swe244p.ex21_display;

import java.awt.*;
import javax.swing.*;

/**
 * HardWareDisplay
 *
 * It is a low-level display class which can only perform operations based on single char
 *
 */
public class HardWareDisplay implements HardWareInterface {

  private JFrame window;
  private CharLabel[][] chars;
  private static final int ROWS = 20;
  private static final int COLS = 30;

  public HardWareDisplay() {
    window = new JFrame();
    var container = window.getContentPane();
    container.setLayout(new GridLayout(ROWS, COLS));

    chars = new CharLabel[ROWS][COLS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        chars[i][j] = new CharLabel();
        container.add(chars[i][j]);
      }
    }

    window.pack();
    window.setVisible(true);
  }

  public int getRowCounts() {
    return ROWS;
  }

  public int getColCounts() {
    return COLS;
  }

  public void write(int row, int col, char c) {
    chars[row][col].write(c);
  }

}


class CharLabel extends JLabel {

  private static final long serialVersionUID = 1L;
  private static final Color bgColor = Color.GRAY;
  private static final Color fgColor = Color.YELLOW;

  public CharLabel() {
    setText(" ");
    setFont(new Font("Monospaced", Font.BOLD, 20));
    setBackground(bgColor);
    setForeground(fgColor);
    setOpaque(true);
  }


  public void write(final char c) {
    /**
     * Causes doRun.run() to be executed asynchronously on the AWT event dispatching thread. This
     * will happen after all pending AWT events have been processed. This method should be used when
     * an application thread needs to update the GUI. In the following example the invokeLater call
     * queues the Runnable object doHelloWorld on the event dispatching thread and then prints a
     * message.
     */
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setText(Character.valueOf(c).toString());
      }
    });
  };

}
