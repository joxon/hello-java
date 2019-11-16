package uci.mswe.swe245p_gui.ex11_shape_transformations;

import java.util.Date;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Application: This handles the application workflow, initialization, and command-line parameters.
 */
public class ShapeTransformations extends Application {

  static final int MIN_WINDOW_WIDTH = 640;
  static final int MIN_WINDOW_HEIGHT = 480;
  static final int CIRCLE_RADIUS = 150;

  static Background getBackground(Color color) {
    return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
  }

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

    /**
     * canvas
     */
    // var peterUrl = "https://cnlm.uci.edu/files/bb-plugin/cache/JHvxrQa-circle.png";
    // var peter = new ImagePattern(new Image(peterUrl));
    var circle = new Circle(CIRCLE_RADIUS, Color.WHITE);
    var nameLabel = new Label("Junxian Chen");
    nameLabel.setFont(Font.font("Segoe Script", FontWeight.EXTRA_BOLD, 30.0));
    nameLabel.setTextFill(Color.GRAY);
    var container = new StackPane(); // TODO If you click on the shape, it will "return to normal"
    container.getChildren().addAll(circle, nameLabel);
    container.setPrefSize(CIRCLE_RADIUS, CIRCLE_RADIUS);
    var canvasPane = new StackPane(); // StackPane positions nodes in its center.
    canvasPane.setBackground(getBackground(Color.GRAY));
    canvasPane.getChildren().add(container);

    /**
     * controls
     */

    /**
     * https://stackoverflow.com/questions/37821796/difference-between-setonxxx-method-and-addeventhandler-javafx
     *
     * So if you need only one EventHandler it makes no difference which method you use. However, if
     * you need to apply several EventHandlers for the same EventType you have to add them using
     * addEventhandler
     */

    /**
     * rotate
     */
    var rotateLabel = new Label("Rotate");
    var rotateLeft = new Button("(L)"); // TODO listen to keyboard
    rotateLeft.setOnAction(event -> {
      var newRotate = container.getRotate() - 10;
      if (newRotate < 0) {
        newRotate += 360.0;
      } else if (newRotate > 360.0) {
        newRotate -= 360.0;
      }
      container.setRotate(newRotate);
    });
    var rotateRight = new Button("(R)");
    rotateRight.setOnAction(event -> {
      var newRotate = container.getRotate() + 10;
      if (newRotate < 0) {
        newRotate += 360.0;
      } else if (newRotate > 360.0) {
        newRotate -= 360.0;
      }
      container.setRotate(newRotate);
    });
    var rotateButtonBox = new HBox();
    rotateButtonBox.getChildren().addAll(rotateLeft, rotateRight);
    var rotateBox = new VBox();
    rotateBox.getChildren().addAll(rotateLabel, rotateButtonBox);

    /**
     * move
     *
     * 0 x 1 2 3
     *
     * y
     *
     * 1
     *
     * 2
     */
    var moveLabel = new Label("Move");
    var moveUp = new Button("(↑)");
    moveUp.setOnAction(event -> container.setTranslateY(container.getTranslateY() - 10));
    var moveDown = new Button("(↓)");
    moveDown.setOnAction(event -> container.setTranslateY(container.getTranslateY() + 10));
    var moveLeft = new Button("(←)");
    moveLeft.setOnAction(event -> container.setTranslateX(container.getTranslateX() - 10));
    var moveRight = new Button("(→)");
    moveRight.setOnAction(event -> container.setTranslateX(container.getTranslateX() + 10));
    var moveButtonPane = new GridPane();
    // col, then row
    GridPane.setConstraints(moveUp, 1, 0);
    GridPane.setConstraints(moveDown, 1, 2);
    GridPane.setConstraints(moveLeft, 0, 1);
    GridPane.setConstraints(moveRight, 2, 1);
    moveButtonPane.getChildren().addAll(moveUp, moveDown, moveLeft, moveRight);
    var moveBox = new VBox();
    moveBox.getChildren().addAll(moveLabel, moveButtonPane);

    /**
     * scale
     */
    var scaleLabel = new Label("Scaling");
    var scaleSlider = new Slider(50, 200, 100);
    var scaleText = new TextField("100");
    EventHandler<MouseEvent> handleSliderEvent = event -> {
      var factor = scaleSlider.getValue();
      // TODO: how to scale??
      /**
       * setScaleX(double scale), setScaleY(double scale): Increases (or decreases) the Node by
       * multiplying its horizontal or vertical dimensions by scale
       */
      container.setScaleX(container.getScaleX() * factor / 100);
      container.setScaleY(container.getScaleY() * factor / 100);

      // var length = CIRCLE_RADIUS * factor / 100;
      // container.setPrefSize(length, length);
      // container.setLayoutX(length);
      // container.setLayoutY(length);

      scaleText.setText(String.valueOf(factor).split("[.]")[0]);
    };
    scaleSlider.setOnMouseDragged(handleSliderEvent);
    scaleSlider.setOnMouseClicked(handleSliderEvent);
    // no effects: DragExited DragOver DragDropped DragDone DragEntered
    // setOnDragDetected works badly

    scaleText.setOnAction(event -> {
      try {
        var factor = Double.parseDouble(scaleText.getText());
        container.setScaleX(container.getScaleX() * factor / 100);
        container.setScaleY(container.getScaleY() * factor / 100);

        // var length = CIRCLE_RADIUS * factor / 100;
        // container.setLayoutX(length);
        // container.setLayoutY(length);
        scaleSlider.setValue(factor);
      } catch (NumberFormatException e) {
        scaleText.setText("100");
      }
    });
    var scaleControlBox = new VBox();
    scaleControlBox.getChildren().addAll(scaleSlider, scaleText);
    var scaleBox = new VBox();
    scaleBox.getChildren().addAll(scaleLabel, scaleControlBox);

    /**
     * color
     */
    var colorLabel = new Label("Color");
    var makeWhite = new RadioButton("White");
    var makeBlack = new RadioButton("Black");
    var makeRed = new RadioButton("Red");
    var makeGreen = new RadioButton("Green");
    var makeBlue = new RadioButton("Blue");
    makeRed.setOnAction(event -> {
      if (makeRed.isSelected()) {
        circle.setFill(Color.RED);
        makeGreen.setSelected(false);
        makeBlue.setSelected(false);
        makeBlack.setSelected(false);
        makeWhite.setSelected(false);
      } else {
        makeRed.setSelected(true);
      }
    });
    makeGreen.setOnAction(event -> {
      if (makeGreen.isSelected()) {
        circle.setFill(Color.GREEN);
        makeRed.setSelected(false);
        makeBlue.setSelected(false);
        makeBlack.setSelected(false);
        makeWhite.setSelected(false);
      } else {
        makeGreen.setSelected(true);
      }
    });
    makeBlue.setOnAction(event -> {
      if (makeBlue.isSelected()) {
        circle.setFill(Color.BLUE);
        makeGreen.setSelected(false);
        makeRed.setSelected(false);
        makeBlack.setSelected(false);
        makeWhite.setSelected(false);
      } else {
        makeBlue.setSelected(true);
      }
    });
    makeBlack.setOnAction(event -> {
      if (makeBlack.isSelected()) {
        circle.setFill(Color.BLACK);
        makeGreen.setSelected(false);
        makeBlue.setSelected(false);
        makeRed.setSelected(false);
        makeWhite.setSelected(false);
      } else {
        makeBlack.setSelected(true);
      }
    });
    makeWhite.setOnAction(event -> {
      if (makeWhite.isSelected()) {
        circle.setFill(Color.WHITE);
        makeGreen.setSelected(false);
        makeBlue.setSelected(false);
        makeBlack.setSelected(false);
        makeRed.setSelected(false);
      } else {
        makeWhite.setSelected(true);
      }
    });
    makeWhite.setSelected(true);

    var colorRadioBox = new VBox();
    colorRadioBox.getChildren().addAll(makeRed, makeGreen, makeBlue, makeBlack, makeWhite);
    var colorBox = new VBox();
    colorBox.getChildren().addAll(colorLabel, colorRadioBox);

    /**
     * all controls
     */
    var controlBox = new VBox();
    controlBox.setAlignment(Pos.CENTER);
    controlBox.setBackground(getBackground(Color.LIGHTGRAY));
    controlBox.getChildren().addAll(rotateBox, moveBox, scaleBox, colorBox);
    VBox.setVgrow(rotateBox, Priority.ALWAYS);
    VBox.setVgrow(moveBox, Priority.ALWAYS);
    VBox.setVgrow(scaleBox, Priority.ALWAYS);
    VBox.setVgrow(colorBox, Priority.ALWAYS);

    /**
     * top layout
     */
    var root = new BorderPane();
    root.setLeft(controlBox);
    root.setCenter(canvasPane);

    // Scene: This is the place for the window's content
    var scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);

    // Stage : The JavaFX term for the window
    stage.setScene(scene);
    stage.setTitle("Shape Transformations");
    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);
    stage.show();

    controlBox.toFront();
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
