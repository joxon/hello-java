package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudentRoster implements Initializable {

  public static final String APP_NAME = "Student Roster";
  public static final String DATA_PATH = "data/swe245p_ex21/";
  public static final String DEFAULT_FILE_NAME = "untitled";
  public static final Student DEFAULT_STUDENT = new Student();

  private StringProperty currentFileName = new SimpleStringProperty(DEFAULT_FILE_NAME);

  private ListProperty<Student> studentList =
      new SimpleListProperty<Student>(FXCollections.observableArrayList());

  private IntegerProperty studentIndex = new SimpleIntegerProperty(-1);

  private Stage stage;

  public BorderPane root;

  public FileMenuBar fileMenuBar;
  public SideBar sideBar;

  public RosterTab rosterTab;
  private StudentForm studentForm;
  private StudentTable studentTable;

  public StatsTab statsTab;

  public StatusBar statusBar;

  private static StudentRoster app;

  public static StudentRoster getApp() {
    return app;
  }

  public void setApp(StudentRoster app) {
    StudentRoster.app = app;
  }

  public StudentRoster() {
    setApp(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.studentForm = this.rosterTab.getForm();
    this.studentTable = this.rosterTab.getTable();

    this.studentForm.setFormData(DEFAULT_STUDENT);
    this.studentIndex.addListener((ob, oldv, newv) -> {
      var size = studentList.getSize();
      var newi = newv.intValue();
      if (0 <= newi && newi < size) {
        studentForm.setFormData(studentList.get(newi));
      }
    });
  }

  public StringProperty getCurrentFileName() {
    return currentFileName;
  }

  public void setCurrentFileName(String currentFileName) {
    this.currentFileName.setValue(currentFileName);
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

  public StudentTable getStudentTable() {
    return studentTable;
  }

  public void setStudentTable(StudentTable studentTable) {
    this.studentTable = studentTable;
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
