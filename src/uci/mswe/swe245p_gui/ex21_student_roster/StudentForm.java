package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * StudentForm
 */
public class StudentForm extends GridPane {

  private static final Image DEFAULT_IMAGE =
      new Image("file:data/245p-ex21/peter.png", 100, 100, true, true);

  // ! new Image("file:/data/245p-ex21/peter.png") IS NOT WORKING

  // ! The image is located in default package of the classpath
  // Image image1 = new Image("/flower.png", true);

  // ! The image is located in my.res package of the classpath
  // Image image2 = new Image("my/res/flower.png", 100, 150, false, false);

  // The image is downloaded from the supplied URL through http protocol
  // Image image3 = new Image("http://sample.com/res/flower.png", 100, 0, false, false);

  // ! The image is located in the current working directory
  // Image image4 = new Image("file:flower.png", 0, 100, false, false);

  private static final ObservableList<String> letterList = FXCollections.observableArrayList(//
      "A+", "A", "A-", //
      "B+", "B", "B-", //
      "C+", "C", "C-", //
      "D+", "D", "D-", //
      "F");

  private static final ObservableList<String> passNotPassList =
      FXCollections.observableArrayList("P", "NP");

  private TextField idTextField = new TextField();

  private TextField lastNameTextField = new TextField();

  private TextField firstNameTextField = new TextField();

  private TextField majorTextField = new TextField();

  private CheckBox honorCheckBox = new CheckBox();

  private RadioButton letterGradeRadio = new RadioButton("Letter Grade");

  private RadioButton passNotPassRadio = new RadioButton("Pass / Not pass");

  private TextArea notesTextArea = new TextArea();

  private ImageView imageView = new ImageView(DEFAULT_IMAGE);

  StudentForm() {
    this.setAlignment(Pos.CENTER);
    this.setHgap(10);
    this.setVgap(10);
    this.setPadding(new Insets(25, 25, 25, 25));

    var col0 = new ColumnConstraints();
    col0.setPercentWidth(20);
    var col1 = new ColumnConstraints();
    col1.setPercentWidth(80);
    this.getColumnConstraints().addAll(col0, col1);

    // add(node, col, row)
    var row = 0;

    // ○ ID number
    this.add(new Label("ID"), 0, row);
    this.add(idTextField, 1, row);
    ++row;

    // ○ Last name
    this.add(new Label("Last Name"), 0, row);
    this.add(lastNameTextField, 1, row);
    ++row;

    // ○ First name
    this.add(new Label("First Name"), 0, row);
    this.add(firstNameTextField, 1, row);
    ++row;

    // ○ Major
    this.add(new Label("Major"), 0, row);
    this.add(majorTextField, 1, row);
    ++row;

    // ○ Current grade
    /**
     * The JavaFX ComboBox control enables users to choose an option from a predefined list of
     * choices, or **type in** another value if none of the predefined choices matches what the user
     * want to select.
     *
     * The JavaFX ChoiceBox control enables users to choose an option from a predefined list of
     * choices **only**.
     */
    this.add(new Label("Current Grade"), 0, row);
    var gradeBox = new ChoiceBox<String>();
    gradeBox.setItems(letterList);
    gradeBox.getSelectionModel().selectFirst();
    this.add(gradeBox, 1, row);
    ++row;

    // ○ Grade option (Letter grade or Pass/not pass)
    this.add(new Label("Grade Option"), 0, row);
    letterGradeRadio.setOnAction(e -> {
      letterGradeRadio.setSelected(true);
      passNotPassRadio.setSelected(false);
      gradeBox.setItems(letterList);
      gradeBox.getSelectionModel().selectFirst();
    });
    passNotPassRadio.setOnAction(e -> {
      passNotPassRadio.setSelected(true);
      letterGradeRadio.setSelected(false);
      gradeBox.setItems(passNotPassList);
      gradeBox.getSelectionModel().selectFirst();
    });
    letterGradeRadio.setSelected(true);
    var gradeOptionBox = new HBox(letterGradeRadio, passNotPassRadio);
    gradeOptionBox.setSpacing(10.0);
    this.add(gradeOptionBox, 1, row);
    ++row;

    // ○ Honor student status
    this.add(new Label("Honor Student"), 0, row);
    this.add(honorCheckBox, 1, row);
    ++row;

    // ○ Notes
    this.add(new Label("Notes"), 0, row);
    this.add(notesTextArea, 1, row);
    ++row;

    // Photo (allow uploading of an image file, and display the image)
    this.add(new Label("Photo"), 0, row);
    // new Image path starts at /src
    this.add(imageView, 1, row);
    ++row;

  }

  StudentForm(Student student) {
    this();
    this.setFormData(student);
  }

  public void setFormData(Student student) {
    idTextField.setText(student.id);
    lastNameTextField.setText(student.lastName);
    firstNameTextField.setText(student.firstName);
    majorTextField.setText(student.major);
    honorCheckBox.setSelected(student.honor);
    letterGradeRadio.setSelected(student.gradeOption.equals("LG"));
    passNotPassRadio.setSelected(student.gradeOption.equals("PNP"));
    notesTextArea.setText(student.notes);
    try {
      imageView.setImage(
          new Image("file:" + StudentRoster.DATA_PATH + student.id, 100, 100, true, true));
    } catch (Exception e) {
      System.err.println("Student " + student.id + " has no image.");
    }
  }
}
