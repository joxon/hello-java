package uci.mswe.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
import javafx.stage.FileChooser;

public class StudentForm extends GridPane {

  private static final Image DEFAULT_IMAGE =
      new Image("file:" + StudentRoster.DATA_PATH + "peter.png", 100, 100, true, true);

  public static final ObservableList<String> GRADE_ALL_LIST = FXCollections.observableArrayList(//
      "A+", "A", "A-", //
      "B+", "B", "B-", //
      "C+", "C", "C-", //
      "D+", "D", "D-", //
      "F", "P", "NP");

  public static final ObservableList<String> GRADE_LETTER_LIST = FXCollections.observableArrayList(//
      "A+", "A", "A-", //
      "B+", "B", "B-", //
      "C+", "C", "C-", //
      "D+", "D", "D-", //
      "F");

  public static final ObservableList<String> GRADE_PNP_LIST =
      FXCollections.observableArrayList("P", "NP");

  private TextField idTextField = new TextField();

  private TextField lastNameTextField = new TextField();

  private TextField firstNameTextField = new TextField();

  private TextField majorTextField = new TextField();

  private CheckBox honorCheckBox = new CheckBox();

  private ChoiceBox<String> gradeBox = new ChoiceBox<String>();

  private RadioButton letterGradeRadio = new RadioButton("Letter Grade");

  private RadioButton passNotPassRadio = new RadioButton("Pass / Not pass");

  private TextArea notesTextArea = new TextArea();

  private ImageView imageView = new ImageView(DEFAULT_IMAGE);

  private StudentRoster app;

  public StudentForm() {
    app = StudentRoster.getApp();

    FXMLUtils.loadFXML(this);

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
    this.idTextField.setText(String.valueOf(this.app.getStudentList().getSize() + 1));
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
    gradeBox.setItems(GRADE_LETTER_LIST);
    gradeBox.getSelectionModel().selectFirst();
    this.add(gradeBox, 1, row);
    ++row;

    // ○ Grade option (Letter grade or Pass/not pass)
    this.add(new Label("Grade Option"), 0, row);
    letterGradeRadio.setOnAction(e -> {
      letterGradeRadio.setSelected(true);
      passNotPassRadio.setSelected(false);
      gradeBox.setItems(GRADE_LETTER_LIST);
      gradeBox.getSelectionModel().selectFirst();
    });
    passNotPassRadio.setOnAction(e -> {
      passNotPassRadio.setSelected(true);
      letterGradeRadio.setSelected(false);
      gradeBox.setItems(GRADE_PNP_LIST);
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
    imageView.setOnMouseClicked(e -> setImage());
    // new Image path starts at /src
    this.add(imageView, 1, row);
    ++row;

  }

  StudentForm(Student student) {
    this();
    this.setFormData(student);
  }

  private void setImage() {
    var picChooser = new FileChooser();
    picChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Photos", "*.jpg", "*.jpeg", "*.png", "*.bmp"));
    var photoFile = picChooser.showOpenDialog(this.app.getStage());
    if (photoFile == null) {
      return;
    }
    var id = this.getIdTextField().getText();
    var photoPathString = StudentRoster.DATA_PATH + id;
    var photoFileNew = new File(photoPathString);
    try {
      Files.copy(photoFile.toPath(), photoFileNew.toPath(), StandardCopyOption.REPLACE_EXISTING);
      this.imageView.setImage(new Image("file:" + photoPathString, 100, 100, true, true));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void resetForm() {
    this.setFormData(new Student());
  }

  public void setFormData(Student student) {
    idTextField.setText(student.getId());

    lastNameTextField.setText(student.getLastName());
    firstNameTextField.setText(student.getFirstName());

    majorTextField.setText(student.getMajor());

    if (student.getGradeOption().equals("LG")) {
      gradeBox.setItems(GRADE_LETTER_LIST);
      letterGradeRadio.setSelected(true);
      passNotPassRadio.setSelected(false);
    } else if (student.getGradeOption().equals("PNP")) {
      gradeBox.setItems(GRADE_PNP_LIST);
      letterGradeRadio.setSelected(false);
      passNotPassRadio.setSelected(true);
    }
    gradeBox.getSelectionModel().select(student.getGrade());

    honorCheckBox.setSelected(student.isHonor());

    notesTextArea.setText(student.getNotes());

    var image =
        new Image("file:" + StudentRoster.DATA_PATH + student.getId(), 100, 100, true, true);
    if (image.isError()) {
      imageView.setImage(DEFAULT_IMAGE);
    } else {
      imageView.setImage(image);
    }
  }

  public TextField getIdTextField() {
    return idTextField;
  }

  public void setIdTextField(TextField idTextField) {
    this.idTextField = idTextField;
  }

  public TextField getLastNameTextField() {
    return lastNameTextField;
  }

  public void setLastNameTextField(TextField lastNameTextField) {
    this.lastNameTextField = lastNameTextField;
  }

  public TextField getFirstNameTextField() {
    return firstNameTextField;
  }

  public void setFirstNameTextField(TextField firstNameTextField) {
    this.firstNameTextField = firstNameTextField;
  }

  public TextField getMajorTextField() {
    return majorTextField;
  }

  public void setMajorTextField(TextField majorTextField) {
    this.majorTextField = majorTextField;
  }

  public CheckBox getHonorCheckBox() {
    return honorCheckBox;
  }

  public void setHonorCheckBox(CheckBox honorCheckBox) {
    this.honorCheckBox = honorCheckBox;
  }

  public RadioButton getLetterGradeRadio() {
    return letterGradeRadio;
  }

  public void setLetterGradeRadio(RadioButton letterGradeRadio) {
    this.letterGradeRadio = letterGradeRadio;
  }

  public RadioButton getPassNotPassRadio() {
    return passNotPassRadio;
  }

  public void setPassNotPassRadio(RadioButton passNotPassRadio) {
    this.passNotPassRadio = passNotPassRadio;
  }

  public TextArea getNotesTextArea() {
    return notesTextArea;
  }

  public void setNotesTextArea(TextArea notesTextArea) {
    this.notesTextArea = notesTextArea;
  }

  public ChoiceBox<String> getGradeBox() {
    return gradeBox;
  }

  public void setGradeBox(ChoiceBox<String> gradeBox) {
    this.gradeBox = gradeBox;
  }
}
