package uci.mswe.swe245p_gui;

import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Application: This handles the application workflow, initialization, and command-line parameters.
 */
public class HelloFX extends Application {

  // we are allowed to create UI objects on non-UI thread private final Text txtTime = new Text();
  private final Text timerText = new Text();

  private volatile boolean timerShouldStop = false;

  // this is timer thread which will update out time view every second
  Thread timer = new Thread(() -> {
    while (!timerShouldStop) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        // ignore
      }
      Platform.runLater(() -> {
        // updating live UI object requires JavaFX App Thread
        timerText.setText(new Date().toString());
      });
    }
  });

  /**
   * Overriding this method allows you to run code before the window is created. Usually, this
   * method is used for loading resources, handling command-line parameters, and validating
   * environments. If something is wrong at this stage, you can exit the program with a friendly
   * command-line message without wasting resources on the window's creation.
   */

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());
    timer.start();
  }

  /**
   * This is the main entry point and the only method that is abstract and has to be overridden. The
   * first window of the application has been already prepared and is passed as a parameter.
   *
   * @param stage : The JavaFX term for the window
   */
  @Override
  public void start(Stage stage) {

    // get label and rect ready
    var rectBlack = new Rectangle(500, 400, Color.BLACK);
    var rectIvory = new Rectangle(400, 300, Color.IVORY);

    var javaVersion = System.getProperty("java.version");
    var javafxVersion = System.getProperty("javafx.version");
    var label = new Label("JavaFX " + javafxVersion + " running on Java " + javaVersion);

    final var fontSize = 14.0;
    final var font = Font.font("Hack", FontWeight.NORMAL, fontSize);
    label.setFont(font);
    timerText.setFont(font);

    VBox textBox = new VBox(); // the box takes all the space on the top, but it is transparent
    textBox.setAlignment(Pos.CENTER); // !important
    textBox.getChildren().addAll(label, timerText);

    // StackPane positions nodes in its center.

    var root = new StackPane();
    root.setBackground(
        new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
    root.getChildren().addAll(rectBlack, rectIvory, textBox);

    // add them to the pane in the order from left to right
    // the right most item will be on the top

    // var root = new BorderPane();
    // root.setCenter(rectBlack);
    // root.setCenter(rectIvory);
    // root.setCenter(textBox);

    // bring label to the top
    // label.toFront();

    // Scene: This is the place for the window's content
    Scene scene = new Scene(root, 640, 480);
    stage.setScene(scene);
    stage.setTitle("Hello FX!");

    stage.show();
  }

  /**
   * This is the last user code called before the application exits. You can free external resources
   * here, update logs, or save the application state.
   */
  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
    timerShouldStop = true;
  }

  /**
   * If you need to have control over the moment JavaFX starts, you can use the Application.launch()
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

}
