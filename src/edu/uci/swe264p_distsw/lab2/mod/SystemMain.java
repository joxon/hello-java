package edu.uci.swe264p_distsw.lab2.mod;

/**
 * @(#)SystemMain.java
 *
 * Copyright: Copyright (c) 2003,2004 Carnegie Mellon University
 *
 */

import java.io.File;
import java.io.IOException;

import edu.uci.swe264p_distsw.lab2.mod.database.DataBase;
import edu.uci.swe264p_distsw.lab2.mod.handlers.*;
import edu.uci.swe264p_distsw.lab2.mod.io.*;

/**
 * Class to hold system main method.
 */
class SystemMain {

  /**
   * Creates components and starts the system. Two parameters are expected, the name of the file
   * containing the student data and the name of the file containing the course data.
   *
   * @param args array of input parameters
   */
  public static void main(String args[]) {

    String studentFileName, courseFileName;

    // Check the number of parameters.
    if (args.length == 2) {
      studentFileName = args[0];
      courseFileName = args[1];
    } else {
      studentFileName = "data/swe264p_lab2/Students.txt";
      courseFileName = "data/swe264p_lab2/Courses.txt";
    }

    // Check if input files exists.
    if (new File(studentFileName).exists() == false) {
      System.err.println("Could not find " + studentFileName);
      System.exit(1);
    }
    if (new File(courseFileName).exists() == false) {
      System.err.println("Could not find " + courseFileName);
      System.exit(1);
    }

    // Create components.
    try {
      final var db = new DataBase(studentFileName, courseFileName);

      // Initialize event bus.
      EventBus.initialize();

      // ! Creating new handlers depends on DataBase and EventBus
      new ListAllStudentsHandler(db, EventBus.EV_LIST_ALL_STUDENTS, EventBus.EV_SHOW);
      new ListAllCoursesHandler(db, EventBus.EV_LIST_ALL_COURSES, EventBus.EV_SHOW);
      new ListStudentsRegisteredHandler(db, EventBus.EV_LIST_STUDENTS_REGISTERED, EventBus.EV_SHOW);
      new ListCoursesRegisteredHandler(db, EventBus.EV_LIST_COURSES_REGISTERED, EventBus.EV_SHOW);
      new ListCoursesCompletedHandler(db, EventBus.EV_LIST_COURSES_COMPLETED, EventBus.EV_SHOW);
      new RegisterStudentHandler(db, EventBus.EV_REGISTER_STUDENT, EventBus.EV_SHOW);
    } catch (IOException e) {
      // Dump the exception information for debugging.
      e.printStackTrace();
      System.exit(1);
    }

    // Initialize output.
    new ClientOutputConsole();
    new ClientOutputLogFile();

    // Start the system.
    new ClientInput().start();
  }
}
