package edu.uci.swe244p.ex34_display_v2;

import java.awt.*;
import javax.swing.*;

public class JDisplay implements HWDisplay {

    private JFrame win;
    private CharDisplay[][] text;
    private static int ROWS = 20;
    private static int COLS = 30;

    public JDisplay() {
        win = new JFrame();
        win.getContentPane().setLayout(new GridLayout(ROWS, COLS));
        text = new CharDisplay[ROWS][COLS];
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                text[i][j] = new CharDisplay();
                win.getContentPane().add(text[i][j]);
            }
        win.pack();
        win.setVisible(true);
    }

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }

    public void write(int row, int col, char c) {
        text[row][col].write(c);
    }

}


class CharDisplay extends JLabel {

    /**
    *
    */
    private static final long serialVersionUID = 1L;
    private static final Color bgColor = Color.GRAY;
    private static final Color fgColor = Color.YELLOW;

    public CharDisplay() {
        setText(" ");
        setFont(new Font("Monospaced", Font.BOLD, 20));
        setBackground(bgColor);
        setForeground(fgColor);
        setOpaque(true);
    }


    public void write(final char c) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setText(Character.valueOf(c).toString());
            }
        });
    };
}
