package uci.mswe.swe245p_gui.ex31_applying_fxml_to_ex11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShapeTransformationsFXML extends Application {

  private static final int MIN_WINDOW_WIDTH = 800;
  private static final int MIN_WINDOW_HEIGHT = 600;
  private static final String FXML_NAME = "ShapeTransformations.fxml";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final var root = (BorderPane) FXMLLoader.load(getClass().getResource(FXML_NAME));
    final var scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Shape Transformations");
    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);
    stage.show();
  }
}
