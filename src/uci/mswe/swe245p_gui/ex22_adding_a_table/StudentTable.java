package uci.mswe.swe245p_gui.ex22_adding_a_table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import uci.mswe.swe245p_gui.ex21_student_roster.Student;
import uci.mswe.swe245p_gui.ex21_student_roster.StudentRoster;

/**
 * StudentTable
 */
public class StudentTable extends TableView<Student> {

  private static final Image DEFAULT_IMAGE =
      new Image("file:" + StudentRoster.DATA_PATH + "peter.png", 100, 100, true, true);

  // private StudentRoster app;

  StudentTable(StudentRoster app) {
    // this.app = app;

    final var columnPhotoWidth = 40.0;
    var columnPhoto = new TableColumn<Student, String>("Photo");
    columnPhoto.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
    columnPhoto
        .setCellFactory(new Callback<TableColumn<Student, String>, TableCell<Student, String>>() {

          @Override
          public TableCell<Student, String> call(TableColumn<Student, String> column) {
            final var imageView = new ImageView();
            imageView.setFitHeight(columnPhotoWidth);
            imageView.setFitWidth(columnPhotoWidth);
            TableCell<Student, String> cell = new TableCell<Student, String>() {
              @Override
              protected void updateItem(String id, boolean empty) {
                var image = new Image("file:" + StudentRoster.DATA_PATH + id, 100, 100, true, true);
                if (image.isError()) {
                  imageView.setImage(DEFAULT_IMAGE);
                } else {
                  imageView.setImage(image);
                }
              }
            };
            cell.setGraphic(imageView);
            return cell;
          }

        });
    columnPhoto.setMinWidth(columnPhotoWidth);
    columnPhoto.setMaxWidth(columnPhotoWidth);
    this.getColumns().add(columnPhoto);


    var columnId = new TableColumn<Student, String>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
    // columnId.setMaxWidth(400.0);
    this.getColumns().add(columnId);

    var columnLastName = new TableColumn<Student, String>("Last Name");
    columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
    // columnLastName.setMaxWidth(400.0);
    this.getColumns().add(columnLastName);

    var columnFirstName = new TableColumn<Student, String>("First Name");
    columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
    // columnFirstName.setMaxWidth(400.0);
    this.getColumns().add(columnFirstName);

    var columnMajor = new TableColumn<Student, String>("Major");
    columnMajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
    // columnMajor.setMaxWidth(60.0);
    this.getColumns().add(columnMajor);

    var columnGrade = new TableColumn<Student, String>("Grade");
    columnGrade.setCellValueFactory(new PropertyValueFactory<Student, String>("grade"));
    // columnGrade.setMaxWidth(20.0);
    this.getColumns().add(columnGrade);

    var columnGradeOption = new TableColumn<Student, String>("Grade Option");
    columnGradeOption.setCellValueFactory(new PropertyValueFactory<Student, String>("gradeOption"));
    // columnGradeOption.setMaxWidth(20.0);
    this.getColumns().add(columnGradeOption);

    var columnHonor = new TableColumn<Student, Boolean>("Honor Student");
    columnHonor.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("honor"));
    // columnHonor.setMaxWidth(20.0);
    this.getColumns().add(columnHonor);

    var columnNotes = new TableColumn<Student, String>("Notes");
    columnNotes.setCellValueFactory(new PropertyValueFactory<Student, String>("notes"));
    this.getColumns().add(columnNotes);

    this.setItems(app.getStudentList());

    this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
  }
}
