package uci.mswe.swe245p_gui.ex11_shape_transformations;

import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Application: This handles the application workflow, initialization, and command-line parameters.
 */
public class ShapeTransformations extends Application {

  /**
   * Overriding this method allows you to run code before the window is created. Usually, this
   * method is used for loading resources, handling command-line parameters, and validating
   * environments. If something is wrong at this stage, you can exit the program with a friendly
   * command-line message without wasting resources on the window's creation.
   */

  @Override
  public void init() throws Exception {
    System.out.println("init(): " + new Date());
  }

  /**
   * This is the main entry point and the only method that is abstract and has to be overridden. The
   * first window of the application has been already prepared and is passed as a parameter.
   *
   * @param stage : The JavaFX term for the window
   */
  @Override
  public void start(Stage stage) {

    var rotateLeft = new Button("Rotate left (L)");
    var rotateRight = new Button("Rotate right (R)");
    var rotateBox = new VBox();
    rotateBox.getChildren().addAll(rotateLeft, rotateRight);


    var moveUp = new Button("Move up (↑)");
    var moveDown = new Button("Move down (↓)");
    var moveLeft = new Button("Move left (←)");
    var moveRight = new Button("Move right (→)");
    var moveBox = new VBox();
    moveBox.getChildren().addAll(moveUp, moveDown, moveLeft, moveRight);

    var scaler = new Slider();
    var scale = new TextField();
    var scaleBox = new VBox();
    scaleBox.getChildren().addAll(scaler, scale);

    var makeRed = new RadioButton("Red");
    var makeGreen = new RadioButton("Green");
    var makeBlue = new RadioButton("Blue");
    var makeBlack = new RadioButton("Black");
    var makeWhite = new RadioButton("White");
    var colorBox = new VBox();
    colorBox.getChildren().addAll(makeRed, makeGreen, makeBlue, makeBlack, makeWhite);

    // Vertical Box
    var controlBox = new VBox();
    controlBox.setAlignment(Pos.CENTER);
    controlBox.getChildren().addAll(rotateBox, moveBox, scaleBox, colorBox);

    // StackPane positions nodes in its center.
    var canvasPane = new StackPane();
    var peterUrl = "https://cnlm.uci.edu/files/bb-plugin/cache/JHvxrQa-circle.png";
    var peter = new ImagePattern(new Image(peterUrl));
    var circle = new Circle(200, peter);
    canvasPane.getChildren().add(circle);

    var root = new BorderPane();
    root.setLeft(controlBox);
    root.setCenter(canvasPane);

    // Scene: This is the place for the window's content
    var scene = new Scene(root, 640, 480);
    scene.setFill(Color.ORANGERED);

    // Stage : The JavaFX term for the window
    stage.setScene(scene);
    stage.setTitle("Shape Transformations");
    stage.show();
  }

  /**
   * This is the last user code called before the application exits. You can free external resources
   * here, update logs, or save the application state.
   */
  @Override
  public void stop() throws Exception {
    System.out.println("stop(): " + new Date());
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
