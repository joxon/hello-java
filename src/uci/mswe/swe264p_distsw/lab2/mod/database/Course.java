package uci.mswe.swe264p_distsw.lab2.mod.database;

/**
 * @(#)Course.java
 *
 * Copyright: Copyright (c) 2003, 2004 Carnegie Mellon University
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class represents a record of a course. This class contains a course's general information
 * and the students who have registered this course. This class is constructed from a
 * field-oriented and space-separated string. For the detailed format of such a string refer to
 * {@link #Course(String)}.
 *
 * @author Jung Soo Kim
 * @version 1.0
 */
public class Course {

  /**
   * A string representing this course's ID.
   */
  protected String sCID;

  /**
   * A string representing this course's section.
   */
  protected String sSection;

  /**
   * A string representing this course's class days.
   */
  protected String sDays;

  /**
   * A string representing this course's start time.
   */
  protected int iStart;

  /**
   * A string representing this course's stop time.
   */
  protected int iStop;

  /**
   * A string representing this course's instructor.
   */
  protected String sInstructor;

  /**
   * A string representing this course's name.
   */
  protected String sName;

  /**
   * A list containing the students who registered for this course. Elements in the list are
   * <code>Student</code> objects representing records of registered students.
   */
  protected ArrayList vRegistered;

  /**
   * Constructs a course record by parsing the given string. The string <code>sInput</code> is
   * field-oriented and space-separated. The seven required fields are course ID, section, class
   * days, start time, stop time, instructor, and class name. Here is an example:
   * <p>
   * <code>17651 A MW 1030 1200 Garlan Models of Software Systems</code>
   * <p>
   * Though not necessary, avoid embedding newline characters in the <code>sInput</code> string.
   *
   * @param sInput the string to be parsed representing a course record
   */
  public Course(String sInput) {
    // Prepare to tokenize the input string.
    StringTokenizer objTokenizer = new StringTokenizer(sInput);

    // Get this course's ID, section, time, and instructor.
    this.sCID = objTokenizer.nextToken();
    this.sSection = (objTokenizer.nextToken()).toLowerCase();
    this.sDays = objTokenizer.nextToken();
    this.iStart = Integer.parseInt(objTokenizer.nextToken());
    this.iStop = Integer.parseInt(objTokenizer.nextToken());
    this.sInstructor = objTokenizer.nextToken();

    // Get this course's name.
    this.sName = objTokenizer.nextToken();
    while (objTokenizer.hasMoreTokens()) {
      this.sName = this.sName + " " + objTokenizer.nextToken();
    }

    // Prepare to store students who will register for this course.
    this.vRegistered = new ArrayList();
  }

  /**
    * Test if the given string <code>sCID</code> is equal to the ID of this course record.
    *
    * @param  sCID a string representing a course ID
    * @return <code>true</code> if <code>sCID</code> is equal to the ID of this course record
    * @see    #match(String,String)
    */
  public boolean match(String sCID) {
    return this.sCID.equals(sCID);
  }

  /**
    * Test if the given strings <code>sCID</code> and <code>sSection</code> are equal to ID and
    * section of this course record respectively.
    *
    * @param  sCID a string representing a course ID
    * @param  sSection a string representing a course section
    * @return <code>true</code> if <code>sCID</code> and <code>sSection</code> are equal to the ID
    *         section of this course record
    * @see    #match(String)
    */
  public boolean match(String sCID, String sSection) {
    return this.sCID.equals(sCID) && this.sSection.equals(sSection.toLowerCase());
  }

  /**
    * Test if the given course record <code>objCourse</code> and this course record have any time
    * conflict.
    *
    * @param  objCourse a course object to test
    * @return <code>true</code> if <code>objCourse</code> conflicts with this course record
    */
  public boolean conflicts(Course objCourse) {
    // Two courses with the same ID and section conflict.
    if (this.sCID.equals(objCourse.sCID) && this.sSection.equals(objCourse.sSection)) {
      return true;
    }

    // Two courses with overlapping time conflict.
    for (int i = 0; i < this.sDays.length(); i++) {
      for (int j = 0; j < objCourse.sDays.length(); j++) {
        if (this.sDays.regionMatches(i, objCourse.sDays, j, 1)) {
          return (this.iStart <= objCourse.iStart && objCourse.iStart < this.iStop)
              || (objCourse.iStart <= this.iStart && this.iStart < objCourse.iStop) ? true : false;
        }
      }
    }
    return false;
  }

  /**
    * Return the name of this course record.
    *
    * @return the name of this course record
    */
  public String getName() {
    return this.sName;
  }

  /**
    * Return a list of students who registered for this course.
    *
    * @return the students who registered for this course as an <code>ArrayList</code> of
    *         <code>Students</code>s
    */
  public ArrayList getRegisteredStudents() {
    return this.vRegistered;
  }

  /**
   * Register a student.
   *
   * @param the reference to the student object to register.
   * @see   Student#registerCourse(Course)
   */
  public void registerStudent(Student objStudent) {
    this.vRegistered.add(objStudent);
  }

  /**
   * Returns a string representation of this course record. The resulting string will be in the
   * same format as the argument for the constructor of this class.
   *
   * @return a string representation of this course record
   * @see    #Course(String)
   * @see    Student#toString()
   */
  public String toString() {
    // Create a string to return with course ID, name, and program, course ID, section, class
    // days, start time, stop time, and the instructor.
    return this.sCID + " " + this.sSection + " " + this.sDays + " " + this.iStart + " " + this.iStop + " "
        + this.sInstructor + " " + this.sName;
  }
}
