package uci.mswe.swe245p_gui.ex21_student_roster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

/**
 * FileMenuBar
 */
public class FileMenuBar extends MenuBar {

  private StudentRoster app;
  private FileChooser fileChooser;

  FileMenuBar(StudentRoster app) {
    this.app = app;
    fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Roster Files", "*.roster"));
    fileChooser.setInitialDirectory(new File(StudentRoster.DATA_PATH));

    // • The below file manipulation features to manage/edit rosters, accessed through a "File" menu
    // in the menu bar.
    // ○ Create a new file
    // ○ Open an existing file
    // ○ Save a file
    // ○ "Save As" a file with a new name
    // ○ Close a file
    // Exit the application

    var fileMenu = new Menu("File");

    var itemCreate = new MenuItem("Create File");
    itemCreate.setOnAction(e -> create());

    var itemOpen = new MenuItem("Open File...");
    itemOpen.setOnAction(e -> open());

    var itemSave = new MenuItem("Save");
    itemSave.setOnAction(e -> save());

    var itemSaveAs = new MenuItem("Save As...");
    itemSaveAs.setOnAction(e -> saveAs());

    var itemClose = new MenuItem("Close File");
    itemClose.setOnAction(e -> close());

    var itemExit = new MenuItem("Exit");
    itemExit.setOnAction(e -> exit());

    fileMenu.getItems().addAll(itemCreate, itemOpen, itemSave, itemSaveAs, itemClose, itemExit);

    this.getMenus().add(fileMenu);
  }

  private void create() {
    app.setStudentList(new SimpleListProperty<Student>());
    app.setCurrentFileName(StudentRoster.DEFAULT_FILE_NAME);
  }

  @SuppressWarnings("unchecked")
  private void open() {
    try {
      var fin = fileChooser.showOpenDialog(app.getStage());
      if (fin == null) {
        return;
      }
      var oos = new ObjectInputStream(new FileInputStream(fin));
      app.setStudentList((SimpleListProperty<Student>) oos.readObject());
      oos.close();

      var filename = fin.getName();
      System.out.println(filename + " opened.");
      app.setCurrentFileName(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void save() {
    var filename = app.getCurrentFileName();
    if (filename.get().equals(StudentRoster.DEFAULT_FILE_NAME)) {
      saveAs();
    } else {
      try {
        var fout = new File(StudentRoster.DATA_PATH + filename);
        var oos = new ObjectOutputStream(new FileOutputStream(fout));
        oos.writeObject(app.getStudentList());
        oos.close();
        System.out.println(filename + " saved.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

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
      oos.writeObject(app.getStudentList());
      oos.close();

      // make sure it is done before changing the UI
      app.setCurrentFileName(filename);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void close() {
    app.setCurrentFileName(StudentRoster.DEFAULT_FILE_NAME);
    app.setStudentList(new SimpleListProperty<Student>());
  }

  private void exit() {
    Platform.exit();
  }
}
