package uci.mswe.swe264p_distsw.lab2.mod.io;

import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

import uci.mswe.swe264p_distsw.lab2.mod.*;

@SuppressWarnings("deprecation")
public class ClientOutputLogFile implements Observer {

  private static PrintWriter out;

  public ClientOutputLogFile() {
    // Subscribe to SHOW event.
    EventBus.subscribeTo(EventBus.EV_SHOW, this);

    final String PATH = "data/swe264p_lab2/";
    final String NAME = Utils.Logger.dateTime().replaceAll("[:]", "-") + ".log";
    out = Utils.createPrintWriter(PATH + NAME);
  }

  public void update(Observable event, Object param) {
    // Write the event parameter (a string) into log file.
    out.println((String) param);
  }

  public static void close() {
    out.close();
  }
}
