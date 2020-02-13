package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

public class FileMenuBar extends MenuBar {

  private StudentRoster app;
  private FileChooser fileChooser;

  // must be public!!
  public FileMenuBar() {
    // https://isumif.github.io/posts/java/2017-12-19-javafx-custom-component.html
    // ! lifesaver
    FXMLUtils.loadFXML(this);

    app = StudentRoster.getApp();
    fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Roster Files", "*.roster"));
    fileChooser.setInitialDirectory(new File(StudentRoster.DATA_PATH));
  }

  @FXML
  private void create() {
    app.getStudentList().set(FXCollections.observableArrayList());
    app.setStudentIndex(-1);
    app.setCurrentFileName(StudentRoster.DEFAULT_FILE_NAME);
    app.getStudentForm().resetForm();
  }

  @FXML
  @SuppressWarnings("unchecked")
  private void open() {
    try {
      var fin = fileChooser.showOpenDialog(app.getStage());
      if (fin == null) {
        return;
      }
      var oos = new ObjectInputStream(new FileInputStream(fin));
      var list = FXCollections.observableArrayList((ArrayList<Student>) oos.readObject());
      oos.close();

      app.getStudentList().set(list);
      app.setStudentIndex(list.size() > 0 ? 0 : -1);

      var filename = fin.getName();
      System.out.println(filename + " opened.");
      app.setCurrentFileName(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void save() {
    var filename = app.getCurrentFileName().get();
    if (filename.equals(StudentRoster.DEFAULT_FILE_NAME)) {
      saveAs();
    } else {
      try {
        var fout = new File(StudentRoster.DATA_PATH + filename);
        var oos = new ObjectOutputStream(new FileOutputStream(fout));
        oos.writeObject(new ArrayList<Student>(app.getStudentList().get()));
        oos.close();
        System.out.println(filename + " saved.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  private void saveAs() {
    try {
      var fout = fileChooser.showSaveDialog(app.getStage());
      if (fout == null) {
        return;
      }

      var filename = fout.getName();
      if (fout.createNewFile()) {
        System.out.println(filename + " created.");
      } else {
        System.out.println(filename + " already exists and will be overwritten.");
      }

      var oos = new ObjectOutputStream(new FileOutputStream(fout));
      // ListProperty cannot be serialized
      oos.writeObject(new ArrayList<Student>(app.getStudentList().get()));
      oos.close();

      // make sure it is done before changing the UI
      app.setCurrentFileName(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void close() {
    create();
  }

  @FXML
  private void exit() {
    Platform.exit();
  }
}
