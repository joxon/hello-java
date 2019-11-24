package uci.mswe.swe245p_gui.ex21_student_roster;

import java.io.Serializable;

/**
 * Student
 */
public class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  // ○ ID number
  String id = "";

  // ○ Last name
  String lastName = "";

  // ○ First name
  String firstName = "";

  // ○ Major
  String major = "";

  // ○ Current grade
  String grade = "A+";

  // ○ Grade option (Letter grade or Pass/not pass)
  // "LG" or "PNP"
  String gradeOption = "LG";

  // ○ Honor student status
  boolean honor = false;

  // ○ Notes
  String notes = "";

  // Photo (allow uploading of an image file, and display the image)

  Student() {

  }

  Student(StudentForm form) {
    this.setFormData(form);
  }

  public void setFormData(StudentForm form) {
    this.id = form.getIdTextField().getText();
    this.lastName = form.getLastNameTextField().getText();
    this.firstName = form.getFirstNameTextField().getText();
    this.major = form.getMajorTextField().getText();
    this.honor = form.getHonorCheckBox().isSelected();
    if (form.getLetterGradeRadio().isSelected()) {
      this.gradeOption = "LG";
    } else if (form.getPassNotPassRadio().isSelected()) {
      this.gradeOption = "PNP";
    }
    this.notes = form.getNotesTextArea().getText();
  }
}
