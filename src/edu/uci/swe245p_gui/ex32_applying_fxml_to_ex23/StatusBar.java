package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar extends HBox implements Initializable {
  public Label status;
  private StudentRoster app;

  public StatusBar() {
    app = StudentRoster.getApp();
    FXMLUtils.loadFXML(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    status.textProperty().bind(Bindings.concat("Current: ", app.getStudentIndex().add(1).asString(),
        "; Total: ", app.getStudentList().sizeProperty().asString()));
  }
}
