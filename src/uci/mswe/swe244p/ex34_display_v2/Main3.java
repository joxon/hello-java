package uci.mswe.swe244p.ex34_display_v2;

import java.util.concurrent.*;
import java.util.Random;

public class Main3 {

    // solve the synchronization problems with a semaphore outside JDisplay2.
    private static final Semaphore semaphore = new Semaphore(1);

    private static void nap(int millisecs) {
        try {
            Thread.sleep(millisecs);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addProc(HighLevelDisplay d) {
        // Add a sequence of addRow operations with short random naps.
        for (int i = 0; i < 100; ++i) {
            try {
                semaphore.acquire();
                d.addRow("row " + i);
                semaphore.release();
            } catch (InterruptedException e) {

            }
            nap(new Random().nextInt(100));
        }
    }

    private static void deleteProc(HighLevelDisplay d) {
        // Add a sequence of deletions of row 0 with short random naps.
        for (int i = 0; i < 100; ++i) {
            try {
                semaphore.acquire();
                d.deleteRow(0);
                semaphore.release();
            } catch (InterruptedException e) {

            }
            nap(new Random().nextInt(2000));
        }
    }

    public static void main(String[] args) {
        final HighLevelDisplay d = new JDisplay2();

        new Thread() {
            public void run() {
                addProc(d);
            }
        }.start();


        new Thread() {
            public void run() {
                deleteProc(d);
            }
        }.start();

    }
}
