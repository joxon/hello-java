package uci.mswe.swe264p_distsw.lab2.mod;

/**
 * @(#)ListAllStudentsHandler.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;

/**
 * "List all students" command event handler.
 */
public class ListAllStudentsHandler extends CommandEventHandler {

  /**
   * Construct "List all students" command event handler.
   *
   * @param objDataBase reference to the database object
   * @param iCommandEvCode command event code to receive the commands to process
   * @param iOutputEvCode output event code to send the command processing result
   */
  public ListAllStudentsHandler(DataBase objDataBase, int iCommandEvCode, int iOutputEvCode) {
    super(objDataBase, iCommandEvCode, iOutputEvCode);
  }

  /**
   * Process "List all students" command event.
   *
   * @param param a string parameter for command
   * @return a string result of command processing
   */
  protected String execute(String param) {
    // Get all student records.
    ArrayList vStudent = this.objDataBase.getAllStudentRecords();

    // Construct a list of student information and return it.
    String sReturn = "";
    for (int i = 0; i < vStudent.size(); i++) {
      sReturn += (i == 0 ? "" : "\n") + ((Student) vStudent.get(i)).toString();
    }
    return sReturn;
  }
}