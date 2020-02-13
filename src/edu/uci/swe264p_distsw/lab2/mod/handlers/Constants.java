package edu.uci.swe264p_distsw.lab2.mod.handlers;

import edu.uci.swe264p_distsw.lab2.mod.database.Course;

/**
 * Constants
 */
public class Constants {

  public static final int OVERBOOK_LIMIT = 3;

  public static String getOverbookString(Course course) {
    int size = course.getRegisteredStudents().size();
    return size > Constants.OVERBOOK_LIMIT ? " (Overbooked! Currently " + size + ")" : " (Currently " + size + ")";
  }

}