package uci.mswe.swe245p_gui.ex11_shape_transformations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Application: This handles the application workflow, initialization, and command-line parameters.
 */
public class ShapeTransformations extends Application {

  private static final int MIN_WINDOW_WIDTH = 800;
  private static final int MIN_WINDOW_HEIGHT = 600;
  private static final int SQUARE_EDGE = 300;

  private Slider scaleSlider;
  private TextField scaleText;

  private List<RadioButton> colorRadioButtons;

  private VBox controlBox;

  private static Background getBackground(final Color color) {
    return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
  }


  class Container extends StackPane {

    Rectangle square;

    Container() {
      square = new Rectangle(SQUARE_EDGE, SQUARE_EDGE, Color.WHITE);

      final var nameLabel = new Label("Junxian Chen");
      nameLabel.setFont(Font.font("Segoe Script", FontWeight.EXTRA_BOLD, 30.0));
      nameLabel.setTextFill(Color.GRAY);

      this.getChildren().addAll(square, nameLabel);
      this.setMaxSize(SQUARE_EDGE, SQUARE_EDGE);
      this.setOnMouseClicked(event -> this.reset());
    }

    public void reset() {

      // var returnXValue = new KeyValue(this.translateXProperty(), 0);
      // var returnYValue = new KeyValue(this.translateYProperty(), 0);
      // var returnFrame = new KeyFrame(Duration.seconds(2), returnXValue, returnYValue);

      final var ONE_SECOND = Duration.seconds(1);

      // this.setTranslateX(0.0);
      // this.setTranslateY(0.0);
      // page 213
      final var returnTransition = new TranslateTransition(ONE_SECOND, this);
      returnTransition.setToX(0);
      returnTransition.setToY(0);


      // this.setRotate(0.0);
      // page 215
      final var rotateTransition = new RotateTransition(ONE_SECOND, this);
      rotateTransition.setToAngle(0);

      // this.setScaleX(1.0);
      // this.setScaleY(1.0);
      // page 216
      final var scaleTransition = new ScaleTransition(ONE_SECOND, this);
      scaleTransition.setToX(1);
      scaleTransition.setToY(1);


      // square.setFill(Color.WHITE);
      // page 217
      final var colorTransition = new FillTransition(ONE_SECOND, square);
      colorTransition.setToValue(Color.WHITE);
      colorRadioButtons.get(0).setSelected(true);
      for (var i = 1; i < colorRadioButtons.size(); ++i) {
        colorRadioButtons.get(i).setSelected(false);
      }

      final var allTransition = new ParallelTransition(returnTransition, rotateTransition,
          scaleTransition, colorTransition);
      controlBox.setDisable(true);
      allTransition.setOnFinished(event -> {
        scaleSlider.setValue(100);
        scaleText.setText("100%");
        controlBox.setDisable(false);
      });
      allTransition.play();
    }

    public void rotateLeft() {
      var newRotate = this.getRotate() - 10;
      if (newRotate < 0) {
        newRotate += 360.0;
      } else if (newRotate > 360.0) {
        newRotate -= 360.0;
      }
      this.setRotate(newRotate);
    }

    public void rotateRight() {
      var newRotate = this.getRotate() + 10;
      if (newRotate < 0) {
        newRotate += 360.0;
      } else if (newRotate > 360.0) {
        newRotate -= 360.0;
      }
      this.setRotate(newRotate);
    }

    public void moveUp() {
      this.setTranslateY(this.getTranslateY() - 10);
    }

    public void moveDown() {
      this.setTranslateY(this.getTranslateY() + 10);
    }

    public void moveLeft() {
      this.setTranslateX(this.getTranslateX() - 10);
    }

    public void moveRight() {
      this.setTranslateX(this.getTranslateX() + 10);
    }

    public void setColor(final Color color) {
      square.setFill(color);
    }
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
  public void start(final Stage stage) {

    /**
     * canvas
     */
    // var peterUrl = "https://cnlm.uci.edu/files/bb-plugin/cache/JHvxrQa-circle.png";
    // var peter = new ImagePattern(new Image(peterUrl));
    final var container = new Container();

    final var canvasPane = new StackPane(); // StackPane positions thiss in its center.
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
    final var rotateLabel = new Label("Rotate");

    Button rotateLeftButton = new Button("(L)");
    rotateLeftButton.setOnAction(event -> container.rotateLeft());

    Button rotateRightButton = new Button("(R)");
    rotateRightButton.setOnAction(event -> container.rotateRight());

    final var rotateButtonBox = new HBox();
    rotateButtonBox.getChildren().addAll(rotateLeftButton, rotateRightButton);

    final var rotateBox = new VBox();
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
    final var moveLabel = new Label("Move");

    final var moveUpButton = new Button("(↑)");
    moveUpButton.setOnAction(event -> container.moveUp());

    final var moveDownButton = new Button("(↓)");
    moveDownButton.setOnAction(event -> container.moveDown());

    final var moveLeftButton = new Button("(←)");
    moveLeftButton.setOnAction(event -> container.moveLeft());

    final var moveRightButton = new Button("(→)");
    moveRightButton.setOnAction(event -> container.moveRight());

    final var moveButtonPane = new GridPane();
    // col, then row
    GridPane.setConstraints(moveUpButton, 1, 0);
    GridPane.setConstraints(moveDownButton, 1, 2);
    GridPane.setConstraints(moveLeftButton, 0, 1);
    GridPane.setConstraints(moveRightButton, 2, 1);
    moveButtonPane.getChildren().addAll(moveUpButton, moveDownButton, moveLeftButton,
        moveRightButton);

    final var moveBox = new VBox();
    moveBox.getChildren().addAll(moveLabel, moveButtonPane);

    /**
     * scale
     */
    final var scaleLabel = new Label("Scaling (50-200%)");

    scaleSlider = new Slider(50, 200, 100);
    scaleSlider.setShowTickLabels(true);
    scaleSlider.setShowTickMarks(true);
    scaleSlider.setMajorTickUnit(50);
    scaleSlider.setMinorTickCount(1);
    scaleSlider.setBlockIncrement(10);

    scaleText = new TextField("100%");
    scaleText.setAlignment(Pos.CENTER);

    final ChangeListener<Number> onChange = (obVal, oldVal, newVal) -> {
      final var factor = newVal.doubleValue(); // 50-200
      final var newScale = factor / 100.0;
      container.setScaleX(newScale);
      container.setScaleY(newScale);
      scaleText.setText(String.valueOf(factor).split("[.]")[0] + "%");
    };
    scaleSlider.valueProperty().addListener(onChange);

    // EventHandler<MouseEvent> onMouseEvent = event -> {
    // var factor = scaleSlider.getValue(); // 50-200
    // var newScale = factor / 100;
    // /**
    // * setScaleX(double scale), setScaleY(double scale): Increases (or decreases) the this by
    // * multiplying its horizontal or vertical dimensions by scale
    // */
    // // ! getScaleX getScaleY are default to 1.0
    // container.setScaleX(newScale);
    // container.setScaleY(newScale);
    // scaleText.setText(String.valueOf(factor).split("[.]")[0] + "%");
    // };
    // scaleSlider.setOnMouseDragged(onMouseEvent);
    // scaleSlider.setOnMouseClicked(onMouseEvent);
    // scaleSlider.setOnDragDetected(handleSliderEvent);
    // no effects: DragExited DragOver DragDropped DragDone DragEntered

    scaleText.setOnAction(event -> {
      try {
        // ? 2%00% -> 200, not important
        final var text = scaleText.getText().replaceAll("[%]", "");
        final var factor = Double.parseDouble(text);
        if (factor < 50 || factor > 200) {
          throw new NumberFormatException();
        }
        final var newScale = factor / 100;
        container.setScaleX(newScale);
        container.setScaleY(newScale);

        scaleSlider.setValue(factor);
        scaleText.setText(text + "%");
      } catch (final NumberFormatException e) {
        scaleText.setText("100%");
      }
    });
    final var scaleControlBox = new VBox();
    scaleControlBox.getChildren().addAll(scaleSlider, scaleText);
    final var scaleBox = new VBox();
    scaleBox.getChildren().addAll(scaleLabel, scaleControlBox);

    /**
     * color
     */
    final var colorLabel = new Label("Color");
    colorRadioButtons = new ArrayList<RadioButton>(5);
    colorRadioButtons.add(new RadioButton("White"));
    colorRadioButtons.add(new RadioButton("Black"));
    colorRadioButtons.add(new RadioButton("Red"));
    colorRadioButtons.add(new RadioButton("Green"));
    colorRadioButtons.add(new RadioButton("Blue"));
    colorRadioButtons.forEach(thisButton -> thisButton.setOnAction(event -> {
      if (thisButton.isSelected()) {
        container.setColor(Color.valueOf(thisButton.getText().toUpperCase()));
        colorRadioButtons.forEach(other -> {
          if (!other.equals(thisButton)) {
            other.setSelected(false);
          }
        });
      } else {
        thisButton.setSelected(true);
      }
    }));
    colorRadioButtons.get(0).setSelected(true);

    // System.out.println(event.getSource().equals(makeRed)); // true
    // System.out.println(Color.RED == Color.valueOf("RED")); // true

    final var colorRadioBox = new VBox();
    colorRadioButtons.forEach(button -> colorRadioBox.getChildren().add(button));
    final var colorBox = new VBox();
    colorBox.getChildren().addAll(colorLabel, colorRadioBox);


    /**
     * all controls
     */
    controlBox = new VBox();
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
    final var root = new BorderPane();
    root.setLeft(controlBox);
    root.setCenter(canvasPane);

    // Scene: This is the place for the window's content
    final var scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
    scene.setOnKeyPressed(event -> {
      // JEP 325: Switch Expressions (Preview): https://openjdk.java.net/jeps/325
      // JEP 354: Switch Expressions (Preview): https://openjdk.java.net/jeps/354
      switch (event.getCode()) {
        case L:
          container.rotateLeft();
          break;

        case R:
          container.rotateRight();
          break;

        // Hold 'Alt' key
        case UP:
          container.moveUp();
          break;

        case DOWN:
          container.moveDown();
          break;

        case LEFT:
          container.moveLeft();
          break;

        case RIGHT:
          container.moveRight();
          break;

        default:
          break;
      }
    });
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
  public static void main(final String[] args) {
    launch(args);
  }

}
