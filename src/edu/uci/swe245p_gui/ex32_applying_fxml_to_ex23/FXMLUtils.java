package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class FXMLUtils {
  public static <T> void loadFXML(T component) {
    FXMLLoader loader = new FXMLLoader();
    loader.setRoot(component);
    loader.setControllerFactory(theClass -> component);

    String fileName = component.getClass().getSimpleName() + ".fxml";
    try {
      loader.load(component.getClass().getResourceAsStream(fileName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
