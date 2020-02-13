package edu.uci.swe245p_gui.ex21_student_roster;

import java.util.Date;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * StudentRoster
 */
public class StudentRoster extends Application {

  public static final String APP_NAME = "Student Roster";
  public static final String DATA_PATH = "data/swe245p_ex21/";
  // (System.getProperty("user.dir") + "/data/swe245p_ex21/").replace("\\", "/").replace("//", "/");

  private static final int MIN_WINDOW_WIDTH = 800;
  private static final int MIN_WINDOW_HEIGHT = 600;

  public static final String DEFAULT_FILE_NAME = "untitled";
  private StringProperty currentFileName = new SimpleStringProperty(DEFAULT_FILE_NAME);

  public static final Student DEFAULT_STUDENT = new Student();
  private ListProperty<Student> studentList =
      new SimpleListProperty<Student>(FXCollections.observableArrayList());
  private IntegerProperty studentIndex = new SimpleIntegerProperty(-1);

  private Stage stage;

  private BorderPane root = new BorderPane();

  private FileMenuBar fileMenuBar = new FileMenuBar(this);
  private SideBar sideBar = new SideBar(this);
  private StudentForm studentForm = new StudentForm(this);
  private StatusBar statusBar = new StatusBar(this);

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());

    // this.studentList.add(DEFAULT_STUDENT);
    this.studentForm.setFormData(DEFAULT_STUDENT);
    this.studentIndex.addListener((ob, oldv, newv) -> {
      var size = studentList.getSize();
      var newi = newv.intValue();
      if (0 <= newi && newi < size) {
        studentForm.setFormData(studentList.get(newi));
      }
    });
  }

  @Override
  public void start(Stage stage) throws Exception {
    this.stage = stage;

    root.setTop(fileMenuBar);
    root.setLeft(sideBar);
    root.setCenter(studentForm);
    root.setBottom(statusBar);

    var scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
    stage.setScene(scene);

    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);
    stage.setTitle(currentFileName.getValue() + " - " + APP_NAME);
    currentFileName.addListener(e -> stage.setTitle(currentFileName.getValue() + " - " + APP_NAME));

    if (getClass().getSimpleName().equals("StudentRoster")) {
      stage.show();
    }
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
  }

  public StringProperty getCurrentFileName() {
    return currentFileName;
  }

  public void setCurrentFileName(String currentFileName) {
    this.currentFileName.setValue(currentFileName);;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public IntegerProperty getStudentIndex() {
    return studentIndex;
  }

  public void setStudentIndex(int studentIndex) {
    this.studentIndex.set(studentIndex);
  }

  public void setStudentIndex(IntegerProperty studentIndex) {
    this.studentIndex = studentIndex;
  }

  public ListProperty<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(ListProperty<Student> studentList) {
    this.studentList = studentList;
  }

  public FileMenuBar getFileMenuBar() {
    return fileMenuBar;
  }

  public void setFileMenuBar(FileMenuBar fileMenuBar) {
    this.fileMenuBar = fileMenuBar;
  }

  public SideBar getSideBar() {
    return sideBar;
  }

  public void setSideBar(SideBar sideBar) {
    this.sideBar = sideBar;
  }

  public StudentForm getStudentForm() {
    return studentForm;
  }

  public void setStudentForm(StudentForm studentForm) {
    this.studentForm = studentForm;
  }

  public StatusBar getStatusBar() {
    return statusBar;
  }

  public void setStatusBar(StatusBar statusBar) {
    this.statusBar = statusBar;
  }

  public BorderPane getRoot() {
    return root;
  }

  public void setRoot(BorderPane root) {
    this.root = root;
  }

}
