package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.io.Serializable;

public class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  // ○ ID number
  private String id = "";

  // ○ Last name
  private String lastName = "";

  // ○ First name
  private String firstName = "";

  // ○ Major
  private String major = "";

  // ○ Current grade
  private String grade = "A+";

  // ○ Grade option (Letter grade or Pass/not pass)
  // "LG" or "PNP"
  private String gradeOption = "LG";

  // ○ Honor student status
  private boolean honor = false;

  // ○ Notes
  private String notes = "";

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

    if (form.getLetterGradeRadio().isSelected()) {
      this.gradeOption = "LG";
    } else if (form.getPassNotPassRadio().isSelected()) {
      this.gradeOption = "PNP";
    }
    this.grade = form.getGradeBox().getSelectionModel().getSelectedItem();

    this.honor = form.getHonorCheckBox().isSelected();

    this.notes = form.getNotesTextArea().getText();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getGradeOption() {
    return gradeOption;
  }

  public void setGradeOption(String gradeOption) {
    this.gradeOption = gradeOption;
  }

  public boolean isHonor() {
    return honor;
  }

  public void setHonor(boolean honor) {
    this.honor = honor;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
