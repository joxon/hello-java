package edu.uci.swe264p_distsw.lab2.mod.io;

/**
 * @(#)ClientInput.java
 *
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 *
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.uci.swe264p_distsw.lab2.mod.*;

/**
 * This class represents a client input component which is responsible for getting user input and
 * sending appropriate command events. These command events may have string parameters that are
 * carried along with the command event. Multiple string parameters are concatenated to a single
 * string separated by spaces.
 *
 * @author Jung Soo Kim
 * @version 1.0
 */
public class ClientInput extends Thread {

  /**
   * Thread body of client input components. It continuously gets user input and announces command
   * events.  It announces show events to request the display of usage prompts.
   */
  public void run() {
    try {
      // Create a buffered reader using system input stream.
      BufferedReader objReader = new BufferedReader(new InputStreamReader(System.in));

      while (true) {
        // Show available commands and get a choice.
        EventBus.announce(EventBus.EV_SHOW, "\nStudent Registration System\n");
        EventBus.announce(EventBus.EV_SHOW, "1) List all students");
        EventBus.announce(EventBus.EV_SHOW, "2) List all courses");
        EventBus.announce(EventBus.EV_SHOW, "3) List students who registered for a course");
        EventBus.announce(EventBus.EV_SHOW, "4) List courses a student has registered for");
        EventBus.announce(EventBus.EV_SHOW, "5) List courses a student has completed");
        EventBus.announce(EventBus.EV_SHOW, "6) Register a student for a course");
        EventBus.announce(EventBus.EV_SHOW, "x) Exit");
        EventBus.announce(EventBus.EV_SHOW, "\nEnter your choice and press return >> ");

        String sChoice = objReader.readLine().trim();

        // Echoing to the log file
        EventBus.announce(EventBus.EV_SHOW, sChoice);

        // Execute command 1: List all students.
        if (sChoice.equals("1")) {
          // Announce the command event #1.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_LIST_ALL_STUDENTS, null);
          continue;
        }

        // Execute command 2: List all courses.
        if (sChoice.equals("2")) {
          // Announce the command event #2.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_LIST_ALL_COURSES, null);
          continue;
        }

        // Execute command 3: List students registered for a course.
        if (sChoice.equals("3")) {
          // Get course ID and course section from user.
          EventBus.announce(EventBus.EV_SHOW, "\nEnter course ID and press return >> ");
          String sCID = objReader.readLine().trim();
          EventBus.announce(EventBus.EV_SHOW, "\nEnter course section and press return >> ");
          String sSection = objReader.readLine().trim();

          // Announce the command event #3 with course ID and course section.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_LIST_STUDENTS_REGISTERED, sCID + " " + sSection);
          continue;
        }

        // Execute command 4: List courses a student has registered for.
        if (sChoice.equals("4")) {
          // Get student ID from user.
          EventBus.announce(EventBus.EV_SHOW, "\nEnter student ID and press return >> ");
          String sSID = objReader.readLine().trim();

          // Announce the command event #4 with student ID.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_LIST_COURSES_REGISTERED, sSID);
          continue;
        }

        // Execute command 5: List courses a student has completed.
        if (sChoice.equals("5")) {
          // Get student ID from user.
          EventBus.announce(EventBus.EV_SHOW, "\nEnter student ID and press return >> ");
          String sSID = objReader.readLine().trim();

          // Announce the command event #5 with student ID.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_LIST_COURSES_COMPLETED, sSID);
          continue;
        }

        // Execute command 6: Register a student for a course.
        if (sChoice.equals("6")) {
          // Get student ID, course ID, and course section from user.
          EventBus.announce(EventBus.EV_SHOW, "\nEnter student ID and press return >> ");
          String sSID = objReader.readLine().trim();
          EventBus.announce(EventBus.EV_SHOW, "\nEnter course ID and press return >> ");
          String sCID = objReader.readLine().trim();
          EventBus.announce(EventBus.EV_SHOW, "\nEnter course section and press return >> ");
          String sSection = objReader.readLine().trim();

          // Announce the command event #5 with student ID, course ID, and course section.
          EventBus.announce(EventBus.EV_SHOW, "\n");
          EventBus.announce(EventBus.EV_REGISTER_STUDENT, sSID + " " + sCID + " " + sSection);
          continue;
        }

        // Terminate this client.
        if (sChoice.equalsIgnoreCase("X")) {
          break;
        }
      }

      // Clean up the resources.
      objReader.close();

      ClientOutputLogFile.close();

    } catch (Exception e) {
      // Dump the exception information for debugging.
      e.printStackTrace();
      System.exit(1);
    }
  }
}
