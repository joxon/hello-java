package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * StatusBar
 */
public class StatusBar extends HBox {

  // private StudentRoster app;

  StatusBar(StudentRoster app) {
    // this.app = app;

    var label = new Label();
    label.textProperty().bind(Bindings.concat("Current: ", app.getStudentIndex().add(1).asString(),
        "; Total: ", app.getStudentList().sizeProperty().asString()));

    this.getChildren().add(label);
  }
}
