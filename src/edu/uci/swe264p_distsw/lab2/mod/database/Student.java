package edu.uci.swe264p_distsw.lab2.mod.database;

/**
 * @(#)Student.java
 *
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class represents a record of a student. This class contains a student's personal information
 * and the course numbers the student has completed. This class is constructed from a field-oriented
 * and space-separated string. For the detailed format of such a string refer to
 * {@link #Student(String)}.
 *
 * @author Jung Soo Kim
 * @version 1.0
 */
public class Student {

  /**
   * A string representing this student's ID.
   */
  protected String sSID;

  /**
   * A string representing this student's last name.
   */
  protected String sName;

  /**
   * A string representing this student's program affiliation.
   */
  protected String sProgram;

  /**
   * A list containing the courses this student has completed. Elements in the list are
   * <code>String</code> objects representing completed course numbers.
   */
  protected ArrayList<String> vCompletedCourseIDs;

  /**
   * A list containing the courses this student is registered for. Elements in the list are
   * <code>Course</code> objects representing records of registered courses.
   */
  protected ArrayList<Course> vRegistered;

  /**
   * The balance in the student account.
   */
  protected int iBalance;

  /**
   * Constructs a student record by parsing the given string. The argument <code>sInput</code> is
   * field-oriented and space-separated string. The first three required fields are student ID,
   * name, and program affiliation. The following field is the student balance.
   * If this student has completed some courses, the course ID of those courses
   * follow. Here is an example:
   * <p>
   * <code>700001234 Carson Kit MSE 2 17651 12345 15123</code>
   * <p>
   * Though not necessary, avoid embedding newline characters in the <code>sInput</code> string.
   *
   * @param sInput the string to be parsed representing a student record
   */
  public Student(String sInput) {
    // Prepare to tokenize the input string.
    StringTokenizer objTokenizer = new StringTokenizer(sInput);

    // Get this student's ID, name, and program.
    this.sSID = objTokenizer.nextToken();
    this.sName = objTokenizer.nextToken();
    this.sName = this.sName + " " + objTokenizer.nextToken();
    this.sProgram = objTokenizer.nextToken();
    this.iBalance = Integer.parseInt(objTokenizer.nextToken());

    // Get the courses this student has completed.
    this.vCompletedCourseIDs = new ArrayList<String>();
    while (objTokenizer.hasMoreTokens()) {
      this.vCompletedCourseIDs.add(objTokenizer.nextToken());
    }

    // Prepare to store the courses this student will register for.
    this.vRegistered = new ArrayList<Course>();
  }

  /**
    * Test if the given string <code>sSID</code> is equal to the ID of this student record.
    *
    * @param  sSID a string representing a student ID
    * @return <code>true</code> if <code>sSID</code> is equal to the ID of this student record
    * @see    #match(String,String)
    */
  public boolean match(String sSID) {
    return this.sSID.equals(sSID);
  }

  /**
   * Return the name of this student record.
   *
   * @return the first and last name of this student record
   */
  public String getName() {
    return this.sName;
  }

  /**
   * Return a list of courses this student has registered for.
   *
   * @return the courses this student has registered for as an <code>ArrayList</code> of
   *         <code>String</code>s
   * @see    #getCompletedCourseIDs()
   */
  public ArrayList<Course> getRegisteredCourses() {
    return this.vRegistered;
  }

  /**
   * Return a list of courses this student has completed.
   *
   * @return the courses this student has completed as an <code>ArrayList</code> of
   *         <code>Course</code>s
   * @see    #getRegisteredCourses()
   */
  public ArrayList<String> getCompletedCourseIDs() {
    return this.vCompletedCourseIDs;
  }

  /**
   * Register for a course.
   *
   * @param objCourse the reference to the course object to register for.
   * @see   Course#registerStudent(Student)
   */
  public void registerCourse(Course objCourse) {
    this.vRegistered.add(objCourse);
  }

  /**
   * Returns a string representation of this student record. The resulting string will be in the
   * same format as the argument for the constructor of this class.
   *
   * @return a string representation of this student record
   * @see    #Student(String)
   * @see    Course#toString()
   */
  public String toString() {
    // Create a string to return using ID, name, and program.
    String sReturn = this.sSID + " " + this.sName + " " + this.sProgram + " " + this.iBalance;

    // Append the completed course numbers.
    for (int i = 0; i < this.vCompletedCourseIDs.size(); i++) {
      sReturn = sReturn + " " + this.vCompletedCourseIDs.get(i).toString();
    }

    return sReturn;
  }
}
