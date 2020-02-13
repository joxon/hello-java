package edu.uci.swe245p_gui.ex41_applying_css_to_ex31;

import javafx.stage.Stage;
import edu.uci.swe245p_gui.ex31_applying_fxml_to_ex11.ShapeTransformationsFXML;

public class ShapeTransformationsFXMLWithCSS extends ShapeTransformationsFXML {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    setFxmlName("ShapeTransformationsWithCSS.fxml");

    super.start(stage);

//    getScene().getStylesheets().add(getClass().getResource("ShapeTransformations.css").toExternalForm());

    if (getClass().getSimpleName().equals("ShapeTransformationsFXMLWithCSS")) {
      stage.show();
    }
  }

}
