package uci.mswe.swe264p_distsw.lab2.mod;

/**
 * @(#)ListCoursesRegisteredHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * "List courses a student has registered for" command event handler.
 */
public class ListCoursesRegisteredHandler extends CommandEventHandler {

  /**
   * Construct "List courses a student has registered for" command event handler.
   *
   * @param objDataBase reference to the database object
   * @param iCommandEvCode command event code to receive the commands to process
   * @param iOutputEvCode output event code to send the command processing result
   */
  public ListCoursesRegisteredHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
    super(objDataBase, iCommandEvCode, iOutputEvCode);
  }

  /**
   * Process "List courses a student has registered for" command event.
   *
   * @param param a string parameter for command
   * @return a string result of command processing
   */
  protected String execute(String param) {
    // Parse the parameters.
    StringTokenizer objTokenizer = new StringTokenizer(param);
    String sSID = objTokenizer.nextToken();

    // Get the list of courses the given student has registered for.
    Student objStudent = this.objDataBase.getStudentRecord(sSID);
    if (objStudent == null) {
      return "Invalid student ID";
    }
    ArrayList vCourse = objStudent.getRegisteredCourses();

    // Construct a list of course information and return it.
    String sReturn = "";
    for (int i = 0; i < vCourse.size(); i++) {
      sReturn += (i == 0 ? "" : "\n") + ((Course) vCourse.get(i)).toString();
    }
    return sReturn;
  }
}