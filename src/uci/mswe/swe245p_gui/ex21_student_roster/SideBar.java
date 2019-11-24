package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * SideBar
 */
public class SideBar extends VBox {

  public static final double MAX_WIDTH = 150.0;

  private StudentRoster app;

  SideBar(StudentRoster app) {
    this.app = app;

    var logo = new SideBarLogo();

    var newStudentButton = new SideBarButton("➕ New Student");
    newStudentButton.setOnAction(e -> newStudent());

    var deleteStudentButton = new SideBarButton("❌ Delete Student");
    deleteStudentButton.setOnAction(e -> deleteStudent());

    var saveChangesButton = new SideBarButton("💾 Save Changes");
    saveChangesButton.setOnAction(e -> saveChanges());

    var nextStudentButton = new SideBarButton("Next Student >>"); // ⏭️
    nextStudentButton.setOnAction(e -> nextStudent());

    var prevStudentButton = new SideBarButton("<< Previous Student"); // ⏮️
    prevStudentButton.setOnAction(e -> prevStudent());

    this.getChildren().addAll(logo, newStudentButton, deleteStudentButton, saveChangesButton,
        nextStudentButton, prevStudentButton);
    this.setSpacing(10.0); // elem to elem
    this.setPadding(new Insets(10.0)); // elem to border
    this.setMaxWidth(MAX_WIDTH);
    this.setAlignment(Pos.CENTER);
    this.setBackground(Utils.getBackground(Color.DARKGRAY));
  }

  private void newStudent() {
    app.getStudentList().add(new Student(app.getStudentForm()));
    app.getStudentIndex().set(app.getStudentList().getSize() - 1);
    // app.getStudentForm().resetForm();
  }

  private void deleteStudent() {
    var size = app.getStudentList().getSize();
    var idx = app.getStudentIndex().get();
    if (idx < 0 || idx >= size) {
      new Alert(AlertType.ERROR, "No student to delete.", ButtonType.OK).showAndWait();
      return;
    }

    app.getStudentList().remove(idx--);

    size = app.getStudentList().getSize();
    if (size == 0) {
      idx = -1;
      app.getStudentIndex().set(idx);
      return;
    } else if (idx < 0 && size > 0) {
      idx = 0;
    }
    app.getStudentIndex().set(idx);
    app.getStudentForm().setFormData(app.getStudentList().get(idx));
  }

  private void saveChanges() {
    var size = app.getStudentList().getSize();
    if (size == 0) {
      return;
    }
    app.getStudentList().set(app.getStudentIndex().get(), new Student(app.getStudentForm()));
    // app.getFileMenuBar().save();
  }

  private void nextStudent() {
    var size = app.getStudentList().getSize();
    if (size == 0) {
      return;
    }
    saveChanges();
    var next = app.getStudentIndex().get() + 1;
    app.getStudentIndex().set(next >= size ? 0 : next);
  }

  private void prevStudent() {
    var size = app.getStudentList().getSize();
    if (size == 0) {
      return;
    }
    saveChanges();
    var prev = app.getStudentIndex().get() - 1;
    app.getStudentIndex().set(prev < 0 ? size - 1 : prev);
  }

}
