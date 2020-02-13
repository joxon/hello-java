package edu.uci.swe245p_gui.ex22_adding_a_table;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import edu.uci.swe245p_gui.ex21_student_roster.Student;
import edu.uci.swe245p_gui.ex21_student_roster.StudentForm;
import edu.uci.swe245p_gui.ex21_student_roster.StudentRoster;

/**
 * StudentTable
 */
public class StudentTable extends TableView<Student> {

    private static final Image DEFAULT_IMAGE =
            new Image("file:" + StudentRoster.DATA_PATH + "peter.png", 100, 100, true, true);

    private StudentRoster app;

    public StudentTable(StudentRoster app) {
        this.app = app;

        /**
         * Photo
         */
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

        /**
         * ID
         */
        var columnId = new TableColumn<Student, String>("Id");
        columnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        columnId.setCellFactory(TextFieldTableCell.forTableColumn());
        columnId.setOnEditCommit(e -> {
            /**
             * https://stackoverflow.com/questions/20798634/restore-oldvalue-in-tableview-after-editing-the-cell-javafx
             *
             * When the backing datamodel is not updated but the tableview shows new edited value, it
             * means that the tableview does not refreshed the rendered values.
             */

            var newId = e.getNewValue();

            if (!newId.matches("\\d+")) {
                e.getTableView().refresh();
                new Alert(AlertType.ERROR, "Invalid student ID. Please retry.", ButtonType.OK)
                        .showAndWait();
                return;
            }

            if (isIdUniqueWhenEditing(newId)) {
                var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
                currentStudent.setId(newId);
                app.getStudentForm().setFormData(currentStudent);
            }
            e.getTableView().refresh();
        });
        // columnId.setMaxWidth(400.0);
        this.getColumns().add(columnId);

        /**
         * Last Name
         */
        var columnLastName = new TableColumn<Student, String>("Last Name");
        columnLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        columnLastName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnLastName.setOnEditCommit(e -> {
            var newLastName = e.getNewValue();

            if (!newLastName.matches("[\\w\\s]+")) {
                e.getTableView().refresh();
                new Alert(AlertType.ERROR, "Invalid last name. Please retry.", ButtonType.OK).showAndWait();
                return;
            }

            var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
            currentStudent.setLastName(newLastName);
            app.getStudentForm().setFormData(currentStudent);
            e.getTableView().refresh();
        });
        // columnLastName.setMaxWidth(400.0);
        this.getColumns().add(columnLastName);

        /**
         * First Name
         */
        var columnFirstName = new TableColumn<Student, String>("First Name");
        columnFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnFirstName.setOnEditCommit(e -> {
            var newFirstName = e.getNewValue();

            if (!newFirstName.matches("[\\w\\s]+")) {
                e.getTableView().refresh();
                new Alert(AlertType.ERROR, "Invalid first name. Please retry.", ButtonType.OK)
                        .showAndWait();
                return;
            }

            var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
            currentStudent.setFirstName(newFirstName);
            app.getStudentForm().setFormData(currentStudent);
            e.getTableView().refresh();
        });
        // columnFirstName.setMaxWidth(400.0);
        this.getColumns().add(columnFirstName);

        /**
         * Major
         */
        var columnMajor = new TableColumn<Student, String>("Major");
        columnMajor.setCellValueFactory(new PropertyValueFactory<Student, String>("major"));
        columnMajor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnMajor.setOnEditCommit(e -> {
            var newMajor = e.getNewValue();

            if (!newMajor.matches("[\\w\\s]+")) {
                e.getTableView().refresh();
                new Alert(AlertType.ERROR, "Invalid major. Please retry.", ButtonType.OK).showAndWait();
                return;
            }

            var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
            currentStudent.setMajor(newMajor);
            app.getStudentForm().setFormData(currentStudent);
            e.getTableView().refresh();
        });
        // columnMajor.setMaxWidth(60.0);
        this.getColumns().add(columnMajor);

        /**
         * Grade
         */
        var columnGrade = new TableColumn<Student, String>("Grade");
        columnGrade.setCellValueFactory(new PropertyValueFactory<Student, String>("grade"));
        columnGrade.setCellFactory(ChoiceBoxTableCell.forTableColumn(StudentForm.GRADE_ALL_LIST));
        columnGrade.setOnEditCommit(e -> {
            var newGrade = e.getNewValue();
            var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
            if ((currentStudent.getGradeOption().equals("LG")
                    && StudentForm.GRADE_LETTER_LIST.contains(newGrade))
                    || (currentStudent.getGradeOption().equals("PNP")
                    && StudentForm.GRADE_PNP_LIST.contains(newGrade))) {
                currentStudent.setGrade(newGrade);
                app.getStudentForm().setFormData(currentStudent);
            } else {
                new Alert(AlertType.ERROR, "Invalid grade for selected grade option. Please retry.",
                        ButtonType.OK).showAndWait();
            }
            e.getTableView().refresh();
        });
        // columnGrade.setMaxWidth(20.0);
        this.getColumns().add(columnGrade);

        /**
         * Grade Option
         */
        var columnGradeOption = new TableColumn<Student, String>("Grade Option");
        columnGradeOption.setCellValueFactory(new PropertyValueFactory<Student, String>("gradeOption"));
        columnGradeOption.setCellFactory(
                ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList("LG", "PNP")));
        columnGradeOption.setOnEditCommit(e -> {
            var newGradeOption = e.getNewValue();
            var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
            currentStudent.setGradeOption(newGradeOption);
            if (newGradeOption.equals("LG")) {
                currentStudent.setGrade(StudentForm.GRADE_LETTER_LIST.get(0));
            } else if (newGradeOption.equals("PNP")) {
                currentStudent.setGrade(StudentForm.GRADE_PNP_LIST.get(0));
            }
            app.getStudentForm().setFormData(currentStudent);
            e.getTableView().refresh();
        });
        // Cannot monitor choiceBox changes??
        // columnGradeOption.textProperty().addListener((ob, ov, nv) -> {
        // if (nv.equals("LG")) {
        // columnGrade
        // .setCellFactory(ChoiceBoxTableCell.forTableColumn(StudentForm.GRADE_LETTER_LIST));
        // } else if (nv.equals("PNP")) {
        // columnGrade.setCellFactory(ChoiceBoxTableCell.forTableColumn(StudentForm.GRADE_PNP_LIST));
        // }
        // });
        // columnGradeOption.setMaxWidth(20.0);
        this.getColumns().add(columnGradeOption);

        /**
         * Honor
         */
        var columnHonor = new TableColumn<Student, CheckBox>("Honor Student?");
        // columnHonor.setCellValueFactory(new PropertyValueFactory<Student, Boolean>("honor"));
        // https://stackoverflow.com/questions/7217625/how-to-add-checkboxs-to-a-tableview-in-javafx
        columnHonor.setCellValueFactory(cell -> {
            var stu = cell.getValue();
            var checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(stu.isHonor());
            checkBox.selectedProperty().addListener((ob, ov, nv) -> {
                stu.setHonor(nv);
                app.getStudentForm().setFormData(app.getStudentList().get(app.getStudentIndex().get()));
            });
            return new SimpleObjectProperty<CheckBox>(checkBox);
        });
        // columnHonor.setCellFactory(column -> new CheckBoxTableCell<>());
        // columnHonor.setCellFactory(CheckBoxTableCell.forTableColumn(columnHonor));
        // columnHonor.setOnEditCommit(e -> {
        // var currentStudent = app.getStudentList().get(app.getStudentIndex().get());
        // currentStudent.setHonor(e.getNewValue().isSelected());
        // app.getStudentForm().setFormData(currentStudent);
        // e.getTableView().refresh();
        // });
        // columnHonor.setMaxWidth(20.0);
        this.getColumns().add(columnHonor);

        var columnNotes = new TableColumn<Student, String>("Notes");
        columnNotes.setCellValueFactory(new PropertyValueFactory<Student, String>("notes"));
        this.getColumns().add(columnNotes);

        this.setItems(app.getStudentList());

        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // If the table is sorted by column then the list will change along
        // The indices will match, so no need to search by ID

        // this.getSelectionModel().selectedItemProperty().addListener((ob, oldv, newv) -> {
        // var stuIdFromTable = newv.getId();
        // var list = app.getStudentList();
        // var size = list.getSize();
        // for (var i = 0; i < size; ++i) {
        // if (list.get(i).getId().equals(stuIdFromTable)) {
        // app.getStudentIndex().set(i);
        // return;
        // }
        // }
        // });

        this.getSelectionModel().selectedIndexProperty().addListener((ob, ov, nv) -> {
            // newIndex can be -1 when table loses focus
            var newIndex = nv.intValue();
            if (newIndex >= 0) {
                app.getStudentIndex().set(nv.intValue());
            }
        });

        this.setEditable(true);
    }

    private boolean isIdUniqueWhenEditing(String id) {
        var list = app.getStudentList();
        var curr = app.getStudentIndex().get();
        var size = list.getSize();
        for (var i = 0; i < size; ++i) {
            if (i == curr) {
                continue;
            } else {
                var stu = list.get(i);
                if (stu.getId().equals(id)) {
                    new Alert(AlertType.ERROR, "Duplicate student ID=" + stu.getId(), ButtonType.OK)
                            .showAndWait();
                    return false;
                }
            }
        }
        return true;
    }
}
