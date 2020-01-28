package uci.mswe.swe264p_distsw.lab2.mod;

/**
 * @(#)ListCoursesCompletedHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * "List courses a student has completed" command event handler.
 */
public class ListCoursesCompletedHandler extends CommandEventHandler {

  /**
   * Construct "List courses a student has completed" command event handler.
   *
   * @param objDataBase reference to the database object
   * @param iCommandEvCode command event code to receive the commands to process
   * @param iOutputEvCode output event code to send the command processing result
   */
  public ListCoursesCompletedHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
    super(objDataBase, iCommandEvCode, iOutputEvCode);
  }

  /**
   * Process "List courses a student has completed" command event.
   *
   * @param param a string parameter for command
   * @return a string result of command processing
   */
  protected String execute(String param) {
    // Parse the parameters.
    StringTokenizer objTokenizer = new StringTokenizer(param);
    String sSID = objTokenizer.nextToken();

    // Get the list of courses the given student has completed.
    Student objStudent = this.objDataBase.getStudentRecord(sSID);
    if (objStudent == null) {
      return "Invalid student ID";
    }
    ArrayList vCourseID = objStudent.getCompletedCourses();

    // Construct a list of course information and return it.
    String sReturn = "";
    for (int i = 0; i < vCourseID.size(); i++) {
      String sCID = (String) vCourseID.get(i);
      String sName = this.objDataBase.getCourseName(sCID);
      sReturn += (i == 0 ? "" : "\n") + sCID + " " + (sName == null ? "Unknown" : sName);
    }
    return sReturn;
  }
}