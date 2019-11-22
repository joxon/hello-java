package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * FileMenuBar
 */
public class FileMenuBar extends MenuBar {

  FileMenuBar() {
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
    var itemOpen = new MenuItem("Open File...");
    var itemSave = new MenuItem("Save");
    var itemSaveAs = new MenuItem("Save As...");
    var itemClose = new MenuItem("Close File");
    var itemExit = new MenuItem("Exit");

    fileMenu.getItems().addAll(itemCreate, itemOpen, itemSave, itemSaveAs, itemClose, itemExit);

    this.getMenus().add(fileMenu);
  }
}
