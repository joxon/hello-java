package edu.uci.swe245p_gui.ex42_applying_css_to_ex32;

import javafx.stage.Stage;
import edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23.StudentRosterFXML;

public class StudentRosterFXMLWithCSS extends StudentRosterFXML {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    setFxmlName("StudentRosterWithCSS.fxml");

    super.start(stage);

    if (getClass().getSimpleName().equals("StudentRosterFXMLWithCSS")) {
      stage.show();
    }
  }
}
