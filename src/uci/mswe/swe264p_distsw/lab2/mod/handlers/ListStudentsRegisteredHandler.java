package uci.mswe.swe264p_distsw.lab2.mod.handlers;

/**
 * @(#)ListStudentsRegisteredHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

import uci.mswe.swe264p_distsw.lab2.mod.*;

/**
 * "List students who registered for a course" command event handler.
 */
public class ListStudentsRegisteredHandler extends CommandEventHandler {

  /**
   * Construct "List students who registered for a course" command event handler.
   *
   * @param objDataBase reference to the database object
   * @param iCommandEvCode command event code to receive the commands to process
   * @param iOutputEvCode output event code to send the command processing result
   */
  public ListStudentsRegisteredHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
    super(objDataBase, iCommandEvCode, iOutputEvCode);
  }

  /**
   * Process "List students who registered for a course" command event.
   *
   * @param param a string parameter for command
   * @return a string result of command processing
   */
  protected String execute(String param) {
    // Parse the parameters.
    StringTokenizer objTokenizer = new StringTokenizer(param);
    String sCID = objTokenizer.nextToken();
    String sSection = objTokenizer.nextToken();

    // Get the list of students who registered for the given course.
    Course objCourse = this.objDataBase.getCourseRecord(sCID, sSection);
    if (objCourse == null) {
      return "Invalid course ID or course section";
    }
    ArrayList vStudent = objCourse.getRegisteredStudents();

    // Construct a list of student information and return it.
    String sReturn = "";
    for (int i = 0; i < vStudent.size(); i++) {
      sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
    }
    return sReturn;
  }
}