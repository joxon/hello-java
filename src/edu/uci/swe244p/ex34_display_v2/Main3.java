package edu.uci.swe244p.ex34_display_v2;

import java.util.concurrent.*;
import java.util.Random;

public class Main3 {

    /**
     * solve the synchronization problems with a semaphore outside JDisplay2.
     *
     * Page 63 - 5.5.3. Semaphores
     *
     * Counting semaphores are used to control the number of activities that can access a certain
     * resource or perform a given action at the same time [CPJ 3.4.1]. Counting semaphores can be
     * used to implement resource pools or to impose a bound on a collection. A Semaphore manages a
     * set of virtual permits; the initial number of permits is passed to the Semaphore constructor.
     * Activities can acquire permits (as long as some remain) and release permits when they are
     * done with them. If no permit is available, acquire blocks until one is (or until interrupted
     * or the operation times out). The release method returns a permit to the semaphore. [4] A
     * degenerate case of a counting semaphore is a binary semaphore, a Semaphore with an initial
     * count of one. A binary semaphore can be used as a mutex with non-reentrant locking semantics;
     * whoever holds the sole permit holds the mutex.
     */
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
