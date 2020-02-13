package edu.uci.swe245p_gui.ex31_applying_fxml_to_ex11;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ShapeTransformationsController implements Initializable {

  public StackPane container;
  public Rectangle square;

  public VBox controlBox;

  public Button rotateLeftButton;
  public Button rotateRightButton;

  public Button moveUpButton;
  public Button moveLeftButton;
  public Button moveDownButton;
  public Button moveRightButton;

  public Slider scaleSlider;
  public TextField scaleText;

  public RadioButton whiteRadio;
  public RadioButton blackRadio;
  public RadioButton redRadio;
  public RadioButton greenRadio;
  public RadioButton blueRadio;

  /**
   * The initialize method is called after all @FXML annotated members have been injected. Suppose you have a table view you want to populate with data:
   * <pre>
   * import javafx.fxml.Initializable;
   *
   * class MyController implements Initializable {
   *   @ FXML private TableView<MyModel> tableView;
   *
   *   @ Override
   *   public void initialize(URL location, ResourceBundle resources) {
   *     tableView.getItems().addAll(getDataFromSource());
   *   }
   * }
   * </pre>
   *
   * @see <a href="https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method">javafx-fxml-controller-constructor-vs-initialize-method</a>
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    scaleSlider.valueProperty().addListener((obVal, oldVal, newVal) -> {
      final var factor = newVal.doubleValue(); // 50-200
      final var newScale = factor / 100.0;
      container.setScaleX(newScale);
      container.setScaleY(newScale);
      scaleText.setText(String.valueOf(factor).split("[.]")[0] + "%");
    });

    scaleText.setOnAction(event -> {
      try {
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

    final var colorRadioButtons = new ArrayList<RadioButton>(5);
    colorRadioButtons.add(whiteRadio);
    colorRadioButtons.add(blackRadio);
    colorRadioButtons.add(redRadio);
    colorRadioButtons.add(greenRadio);
    colorRadioButtons.add(blueRadio);
    colorRadioButtons.forEach(thisButton -> thisButton.setOnAction(event -> {
      if (thisButton.isSelected()) {
        setColor(Color.valueOf(thisButton.getText().toUpperCase()));
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

//    controlBox.setViewOrder(1.0);
    controlBox.toFront();
  }


  public void reset(MouseEvent mouseEvent) {

    final var ONE_SECOND = Duration.seconds(1);

    final var returnTransition = new TranslateTransition(ONE_SECOND, container);
    returnTransition.setToX(0);
    returnTransition.setToY(0);

    final var rotateTransition = new RotateTransition(ONE_SECOND, container);
    rotateTransition.setToAngle(0);

    final var scaleTransition = new ScaleTransition(ONE_SECOND, container);
    scaleTransition.setToX(1);
    scaleTransition.setToY(1);

    final var colorTransition = new FillTransition(ONE_SECOND, square);
    colorTransition.setToValue(Color.WHITE);


    final var allTransition = //
        new ParallelTransition(//
            returnTransition, //
            rotateTransition, //
            scaleTransition, //
            colorTransition);

    allTransition.setOnFinished(event -> {

      scaleSlider.setValue(100);
      scaleText.setText("100%");

      whiteRadio.setSelected(true);
      blackRadio.setSelected(false);
      redRadio.setSelected(false);
      greenRadio.setSelected(false);
      blueRadio.setSelected(false);

      controlBox.setDisable(false);
    });

    controlBox.setDisable(true);
    allTransition.play();
  }

  public void rotateLeft() {
    var newRotate = container.getRotate() - 10;
    if (newRotate < 0) {
      newRotate += 360.0;
    } else if (newRotate > 360.0) {
      newRotate -= 360.0;
    }
    container.setRotate(newRotate);
  }

  public void rotateRight() {
    var newRotate = container.getRotate() + 10;
    if (newRotate < 0) {
      newRotate += 360.0;
    } else if (newRotate > 360.0) {
      newRotate -= 360.0;
    }
    container.setRotate(newRotate);
  }

  public void moveUp() {
    container.setTranslateY(container.getTranslateY() - 10);
  }

  public void moveDown() {
    container.setTranslateY(container.getTranslateY() + 10);
  }

  public void moveLeft() {
    container.setTranslateX(container.getTranslateX() - 10);
  }

  public void moveRight() {
    container.setTranslateX(container.getTranslateX() + 10);
  }

  public void setColor(final Color color) {
    square.setFill(color);
  }

  public void handleKeyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getCode()) {
      case L:
        rotateLeft();
        break;

      case R:
        rotateRight();
        break;

      // Hold 'Alt' key
      case UP:
        moveUp();
        break;

      case DOWN:
        moveDown();
        break;

      case LEFT:
        moveLeft();
        break;

      case RIGHT:
        moveRight();
        break;

      default:
        break;
    }
  }
}


