package uci.mswe.swe245p_gui.ex32_applying_fxml_to_ex23;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StudentRosterFXML extends Application {
  private static final int MIN_WINDOW_WIDTH = 1280;
  private static final int MIN_WINDOW_HEIGHT = 720;

  private String fxmlName = "StudentRoster.fxml";
  private Scene scene;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    final var content = (BorderPane) FXMLLoader.load(getClass().getResource(fxmlName));
    scene = new Scene(content, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
    stage.setScene(scene);
    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);

    stage.setTitle(StudentRoster.getApp().getCurrentFileName().getValue() + " - " + StudentRoster.APP_NAME);
    StudentRoster.getApp().getCurrentFileName().addListener(e -> stage.setTitle(StudentRoster.getApp().getCurrentFileName().getValue() + " - " + StudentRoster.APP_NAME));

    if (getClass().getSimpleName().equals("StudentRosterFXML")) {
      stage.show();
    }
  }
}
