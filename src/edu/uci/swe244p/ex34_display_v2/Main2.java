package edu.uci.swe244p.ex34_display_v2;

public class Main2 {


    private static void nap(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        HighLevelDisplay d = new JDisplay2();

        for (int i = 0; i < 20; i++) {
            d.addRow("AAAAAAAAAAAA  " + i);
            d.addRow("BBBBBBBBBBBB  " + i);
            nap(500);
            d.deleteRow(0);
            nap(500);
        }

    }
}
