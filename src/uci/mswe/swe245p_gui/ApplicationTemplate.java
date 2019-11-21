package uci.mswe.swe245p_gui;

import java.util.Date;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * ApplicationTemplate
 */
public class ApplicationTemplate extends Application {

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
  }

}
