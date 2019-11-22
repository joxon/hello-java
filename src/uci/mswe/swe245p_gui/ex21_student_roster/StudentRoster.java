package uci.mswe.swe245p_gui.ex21_student_roster;

import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * StudentRoster
 */
public class StudentRoster extends Application {

  private String currentFileName = "newRoster";
  public static final String APP_NAME = "Student Roster";
  // public static final String APP_PATH =
  // (System.getProperty("user.dir") + "/data/245p-ex21/").replace("\\", "/").replace("//", "/");

  private static final int MIN_WINDOW_WIDTH = 800;
  private static final int MIN_WINDOW_HEIGHT = 600;

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());
  }

  @Override
  public void start(Stage stage) throws Exception {
    var top = new FileMenuBar();
    var side = new SideBar();
    var form = new StudentForm();

    var root = new BorderPane();
    root.setTop(top);
    root.setLeft(side);
    root.setCenter(form);

    var scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
    stage.setScene(scene);

    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);
    stage.setTitle(currentFileName + " - " + APP_NAME);
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
  }

}
