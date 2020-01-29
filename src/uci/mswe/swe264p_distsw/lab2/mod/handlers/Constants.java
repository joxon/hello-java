package uci.mswe.swe264p_distsw.lab2.mod.handlers;

import uci.mswe.swe264p_distsw.lab2.mod.database.Course;

/**
 * Constants
 */
public class Constants {

  public static final int OVERBOOK_LIMIT = 3;

  public static String getOverbookString(Course course) {
    return course.getRegisteredStudents().size() > Constants.OVERBOOK_LIMIT ? " (Overbooked)" : "";
  }

}