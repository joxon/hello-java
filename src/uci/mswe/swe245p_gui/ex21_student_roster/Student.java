package uci.mswe.swe245p_gui.ex21_student_roster;

import java.io.Serializable;

/**
 * Student
 */
public class Student implements Serializable {

  private static final long serialVersionUID = 1L;

  // ○ ID number
  String id = "111";

  // ○ Last name
  String lastName = "111";

  // ○ First name
  String firstName = "111";

  // ○ Major
  String major = "";

  // ○ Current grade
  String grade = "";

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
}
