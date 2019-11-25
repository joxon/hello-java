package uci.mswe.swe245p_gui.ex21_student_roster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
    app.getStudentList().set(FXCollections.observableArrayList());
    app.setStudentIndex(-1);
    app.setCurrentFileName(StudentRoster.DEFAULT_FILE_NAME);
    app.getStudentForm().resetForm();
  }

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

  public void save() {
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

  private void close() {
    create();
  }

  private void exit() {
    Platform.exit();
  }
}
