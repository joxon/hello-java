package edu.uci.swe264p_distsw.lab2.mod.database;

/**
 * @(#)DataBase.java
 *
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A <code>DataBase</code> provides access to student and course data including reading the record
 * information and writing registration information. Note that current version of database doesn't
 * support writing student and course records to files. A shutdown means loss of all registration
 * information.
 */
public class DataBase {

  /**
   * A list of <code>Student</code> objects containing student records.
   */
  protected ArrayList<Student> vStudent;

  /**
   * A list of <code>Course</code> objects containing course records.
   */
  protected ArrayList<Course> vCourse;

  /**
   * Construct a database that provides access to student and course data. Initial data are filled
   * with the records in the given student and course record files. At the time of creation, the
   * database does not contain any registration information.
   *
   * @param sStudentFileName the name of student record file
   * @param sCourseFileName the name of course record file
   */
  public DataBase(String sStudentFileName, String sCourseFileName) throws FileNotFoundException, IOException {
    // Open the given student and course files.
    BufferedReader objStudentFile = new BufferedReader(new FileReader(sStudentFileName));
    BufferedReader objCourseFile = new BufferedReader(new FileReader(sCourseFileName));

    // Initialize student and course lists.
    this.vStudent = new ArrayList<Student>();
    this.vCourse = new ArrayList<Course>();

    // Populate student and course lists.
    while (objStudentFile.ready()) {
      this.vStudent.add(new Student(objStudentFile.readLine()));
    }
    while (objCourseFile.ready()) {
      this.vCourse.add(new Course(objCourseFile.readLine()));
    }

    // Close the student and course files.
    objStudentFile.close();
    objCourseFile.close();
  }

  /**
   * Return all student records as a list.
   *
   * @return an <code>ArrayList</code> of <code>Student</code> objects containing student records
   */
  public ArrayList<Student> getAllStudentRecords() {
    // Return the student list as it is.
    return this.vStudent;
  }

  /**
   * Return all course records as a list.
   *
   * @return an <code>ArrayList</code> of <code>Course</code> objects containing course records
   */
  public ArrayList<Course> getAllCourseRecords() {
    // Return the course list as it is.
    return this.vCourse;
  }

  /**
   * Return a student record whose ID is equal to the given student ID.
   *
   * @param  sSID a student ID to lookup
   * @return a <code>Student</code> object whose ID is equal to <code>sSID</code>.
   *         <code>null</code> if not found
   * @see    #getStudentName(String)
   */
  public Student getStudentRecord(String sSID) {
    // Lookup and return the matching student record if found.
    for (int i = 0; i < this.vStudent.size(); i++) {
      Student objStudent = (Student) this.vStudent.get(i);
      if (objStudent.match(sSID)) {
        return objStudent;
      }
    }

    // Return null if not found.
    return null;
  }

  /**
   * Return the name of a student whose ID is equal to the given student ID.
   *
   * @param  sSID a student ID to lookup
   * @return a <code>String</code> representing student name. <code>null</code> if not found
   * @see    #getStudentRecord(String)
   */
  public String getStudentName(String sSID) {
    // Lookup and return the matching student name if found.
    for (int i = 0; i < this.vStudent.size(); i++) {
      Student objStudent = (Student) this.vStudent.get(i);
      if (objStudent.match(sSID)) {
        return objStudent.getName();
      }
    }

    // Return null if not found.
    return null;
  }

  /**
   * Return a course record whose ID is equal to the given course ID.
   *
   * @param  sCID a course ID to lookup
   * @param  sSection a course section to lookup
   * @return a <code>Course</code> object whose ID is equal to <code>sCID</code>.
   *         <code>null</code> if not found
   * @see    #getCourseName(String)
   */
  public Course getCourseRecord(String sCID, String sSection) {
    // Lookup and return the matching course record if found.
    for (int i = 0; i < this.vCourse.size(); i++) {
      Course objCourse = (Course) this.vCourse.get(i);
      if (objCourse.match(sCID, sSection)) {
        return objCourse;
      }
    }

    // Return null if not found.
    return null;
  }

  /**
   * Return the name of a course whose ID is equal to the given course ID.
   *
   * @param  sCID a course ID to lookup
   * @return a <code>String</code> representing course name. <code>null</code> if not found
   * @see    #getCourseRecord(String,String)
   */
  public String getCourseName(String sCID) {
    // Lookup and return the matching course name if found.
    for (int i = 0; i < this.vCourse.size(); i++) {
      Course objCourse = (Course) this.vCourse.get(i);
      if (objCourse.match(sCID)) {
        return objCourse.getName();
      }
    }

    // Return null if not found.
    return null;
  }

  /**
   * Make a registration. No conflict check is done before updating the database. Nothing happens
   * if there is no matching student record and/or course record.
   *
   * @param  sSID a student ID to register
   * @param  sCID a course ID to register
   * @param  sSection a course section to register
   */
  public void makeARegistration(String sSID, String sCID, String sSection) {
    // Find the student record and the course record.
    Student objStudent = this.getStudentRecord(sSID);
    Course objCourse = this.getCourseRecord(sCID, sSection);

    // Make a registration.
    if (objStudent != null && objCourse != null) {
      objStudent.registerCourse(objCourse);
      objCourse.registerStudent(objStudent);
    }
  }
}
