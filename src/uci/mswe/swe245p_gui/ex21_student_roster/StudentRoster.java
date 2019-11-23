package uci.mswe.swe245p_gui.ex21_student_roster;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * StudentRoster
 */
public class StudentRoster extends Application {

  public static final String APP_NAME = "Student Roster";
  public static final String DATA_PATH = "data/245p-ex21/";
  // (System.getProperty("user.dir") + "/data/245p-ex21/").replace("\\", "/").replace("//", "/");

  private static final int MIN_WINDOW_WIDTH = 800;
  private static final int MIN_WINDOW_HEIGHT = 600;

  public static final String DEFAULT_FILE_NAME = "untitled.roster";
  private StringProperty currentFileName = new SimpleStringProperty(DEFAULT_FILE_NAME);

  private List<Student> studentList = new ArrayList<Student>();
  private int studentIndex = -1;

  private Stage stage;

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());
  }

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;

    var top = new FileMenuBar(this);
    var side = new SideBar();
    var form = studentList.size() > 0 ? new StudentForm(studentList.get(0)) : new StudentForm();

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
    stage.setTitle(currentFileName.getValue() + " - " + APP_NAME);
    currentFileName.addListener(e -> stage.setTitle(currentFileName.getValue() + " - " + APP_NAME));
    stage.show();
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
  }

  public String getCurrentFileName() {
    return currentFileName.getValue();
  }

  public void setCurrentFileName(String currentFileName) {
    this.currentFileName.setValue(currentFileName);;
  }

  public List<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public int getStudentIndex() {
    return studentIndex;
  }

  public void setStudentIndex(int studentIndex) {
    this.studentIndex = studentIndex;
  }

}
