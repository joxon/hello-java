package uci.mswe.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import uci.mswe.swe245p_gui.ex21_student_roster.Utils;

public class SideBar extends VBox implements Initializable {
  public static final double MAX_WIDTH = 150.0;

  public SideBarButton newStudentButton;
  public SideBarButton deleteStudentButton;
  public SideBarButton saveChangesButton;
  public SideBarButton nextStudentButton;
  public SideBarButton prevStudentButton;

  private StudentRoster app;

  public SideBar() {
    app = StudentRoster.getApp();
    FXMLUtils.loadFXML(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    deleteStudentButton.disableProperty().bind(this.app.getStudentList().emptyProperty());

    nextStudentButton.disableProperty().bind(Bindings.or(//
        this.app.getStudentList().emptyProperty(), //
        this.app.getStudentList().sizeProperty().isEqualTo(1)));

    prevStudentButton.disableProperty().bind(Bindings.or(//
        this.app.getStudentList().emptyProperty(), //
        this.app.getStudentList().sizeProperty().isEqualTo(1)));

    this.setSpacing(10.0); // elem to elem
    this.setPadding(new Insets(10.0)); // elem to border
    this.setMaxWidth(MAX_WIDTH);
    this.setAlignment(Pos.CENTER);
    this.setBackground(Utils.getBackground(Color.DARKGRAY));
  }

  private boolean isFormValid() {
    var form = app.getStudentForm();

    // non empty id
    if (!form.getIdTextField().getText().matches("\\d+")) {
      new Alert(AlertType.ERROR, "Invalid student ID. Please retry.", ButtonType.OK).showAndWait();
      return false;
    }

    if (!form.getFirstNameTextField().getText().matches("[\\w\\s]+")) {
      new Alert(AlertType.ERROR, "Invalid first name. Please retry.", ButtonType.OK).showAndWait();
      return false;
    }

    if (!form.getLastNameTextField().getText().matches("[\\w\\s]+")) {
      new Alert(AlertType.ERROR, "Invalid last name. Please retry.", ButtonType.OK).showAndWait();
      return false;
    }

    if (!form.getMajorTextField().getText().matches("[\\w\\s]+")) {
      new Alert(AlertType.ERROR, "Invalid major. Please retry.", ButtonType.OK).showAndWait();
      return false;
    }

    return true;
  }

  private boolean isIdUniqueWhenAdding() {
    var list = app.getStudentList();
    var form = app.getStudentForm();
    var dupeList = list.filtered(stu -> stu.getId().equals(form.getIdTextField().getText()));
    if (!dupeList.isEmpty()) {
      new Alert(AlertType.ERROR, "Duplicate student ID: " + dupeList.get(0).getId(), ButtonType.OK)
          .showAndWait();
      return false;
    }
    return true;
  }

  @FXML
  private void newStudent() {
    if (!isFormValid() || !isIdUniqueWhenAdding()) {
      return;
    }
    var stu = new Student(app.getStudentForm());
    app.getStudentList().add(stu);
    app.getStudentIndex().set(app.getStudentList().getSize() - 1);
    // app.getStudentForm().resetForm();
    new Alert(AlertType.INFORMATION, "Added student(ID=" + stu.getId() + ") ", ButtonType.OK)
        .showAndWait();
  }

  @FXML
  private void deleteStudent() {
    var size = app.getStudentList().getSize();
    var idx = app.getStudentIndex().get();
    if (idx < 0 || idx >= size) {
      new Alert(AlertType.ERROR, "No student to delete.", ButtonType.OK).showAndWait();
      return;
    }

    var stu = app.getStudentList().remove(idx--);

    size = app.getStudentList().getSize();
    if (size == 0) {
      idx = -1;
      app.getStudentIndex().set(idx);
      app.getStudentForm().resetForm();
      new Alert(AlertType.INFORMATION, "Deleted student(ID=" + stu.getId() + ") ", ButtonType.OK)
          .showAndWait();
      return;
    } else if (idx < 0 && size > 0) {
      idx = 0;
    }

    app.getStudentIndex().set(idx);
    app.getStudentForm().setFormData(app.getStudentList().get(idx));
    new Alert(AlertType.INFORMATION, "Deleted student(ID=" + stu.getId() + ") ", ButtonType.OK)
        .showAndWait();
  }

  private boolean isIdUniqueWhenSavingChanges() {
    var list = app.getStudentList();
    var form = app.getStudentForm();
    var curr = app.getStudentIndex().get();
    var size = list.getSize();
    for (var i = 0; i < size; ++i) {
      if (i == curr) {
        continue;
      } else {
        var stu = list.get(i);
        if (stu.getId().equals(form.getIdTextField().getText())) {
          new Alert(AlertType.ERROR, "Duplicate student ID=" + stu.getId(), ButtonType.OK)
              .showAndWait();
          return false;
        }
      }
    }
    return true;
  }

  @FXML
  private void saveChanges() {
    if (!isFormValid() || !isIdUniqueWhenSavingChanges() || app.getStudentList().getSize() == 0) {
      return;
    }

    app.getStudentList().set(app.getStudentIndex().get(), new Student(app.getStudentForm()));
    // app.getFileMenuBar().save();
  }

  @FXML
  private void nextStudent() {
    var size = app.getStudentList().getSize();
    if (size == 0 || size == 1) {
      return;
    }
    saveChanges();
    var next = app.getStudentIndex().get() + 1;
    app.getStudentIndex().set(next >= size ? 0 : next);
  }

  @FXML
  private void prevStudent() {
    var size = app.getStudentList().getSize();
    if (size == 0 || size == 1) {
      return;
    }
    saveChanges();
    var prev = app.getStudentIndex().get() - 1;
    app.getStudentIndex().set(prev < 0 ? size - 1 : prev);
  }
}
