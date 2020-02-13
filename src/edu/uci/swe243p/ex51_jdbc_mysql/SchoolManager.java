/**
 * Setting up Connector/J
 *
 * 1. Find mysql-connector-java-8.0.18.jar
 * in C:\Program Files (x86)\MySQL\Connector J 8.0
 * or https://dev.mysql.com/downloads/connector/j/
 * then copy it the /lib folder
 *
 * 2. Add it to the .classpath file with this line:
 * <classpathentry kind="lib" path="lib/mysql-connector-java-8.0.18.jar" />
 *
 * 3. DONE
 */
package edu.uci.swe243p.ex51_jdbc_mysql;

import java.sql.*;

class SchoolManager {

  public static void main(String args[]) {
    /**
     * JDBC URL schema
     *
     * jdbc:mysql://[host1][:port1][,[host2][:port2]]...[/[database]] //
     * [?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
     *
     */
    final String URL = "jdbc:mysql://localhost:3306/";

    while (true) {
      String usr = "";
      String pwd = "";

      boolean authPassed = false;
      var console = System.console();

      if (!authPassed) {
        usr = console.readLine("Username: ");
        pwd = new String(console.readPassword("Password: "));
      }

      // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
      // The try-with-resources statement ensures that each resource is closed at the
      // end of the statement
      try (var conn = java.sql.DriverManager.getConnection(URL, usr, pwd)) {
        /**
         * https://stackoverflow.com/questions/20078586/why-we-use-class-forname-oracle-jdbc-driver-oracledriver-while-connecting-to
         * https://stackoverflow.com/questions/7662902/what-is-the-purpose-of-class-fornamemy-jdbc-driver
         *
         * tl;dr: yes, the only use of that Class.forName() call is to ensure the driver
         * is registered. If you use a current JDK and current JDBC drivers, then this
         * call should no longer be necesary.
         */
        // Class.forName("com.mysql.cj.jdbc.Driver");

        authPassed = true;
        System.out.println("Auth succeeded.");

        // init
        var sm = conn.createStatement();
        init(sm);

        // use school;
        conn.setCatalog("school");
        sm = conn.createStatement();

        final String PROMPT = String.join("\n", //
            "=== Menu ===", //
            "[1]: Add a student", //
            "[2]: Add a course", //
            "[3]: Enroll a student to a course", //
            "[4]: Query students in each course", //
            "[5]: Query courses for each student", //
            "[6]: Query courses for a student on a weekday", //
            "[q]: Quit", //
            "Please input: ");
        var option = console.readLine(PROMPT);
        while (true) {
          switch (option) {
          case "1":
            try {
              var addOneStudent = conn
                  .prepareStatement("INSERT INTO students(student_first_name, student_last_name) VALUES(?, ?)");
              var firstName = console.readLine("First name: ");
              var lastName = console.readLine("Last name: ");
              addOneStudent.setString(1, firstName);
              addOneStudent.setString(2, lastName);
              var rows = addOneStudent.executeUpdate();
              System.out.println(rows + " row(s) affected.");
            } catch (Exception e) {
              System.err.println("Error occured when adding a student.");
              e.printStackTrace();
            }
            break;

          case "2":
            try {
              var addOneCourse = conn.prepareStatement(
                  "INSERT INTO courses(course_name, course_weekday, course_start_time, course_end_time, course_room, course_instrutor) VALUES(?, ?, ?, ?, ?, ?)");
              var name = console.readLine("Name(ex. Introduction to Database): ");
              var weekday = console.readLine("Weekday(ex. Mon, Sun): ");
              var start = console.readLine("Start Time(ex. 09:00): ");
              var end = console.readLine("End Time(ex. 10:00): ");
              var room = console.readLine("Room(ex. ICS 193): ");
              var instrutor = console.readLine("Instrutor(ex. Steve Jobs): ");
              addOneCourse.setString(1, name);
              addOneCourse.setString(2, weekday);
              addOneCourse.setString(3, start);
              addOneCourse.setString(4, end);
              addOneCourse.setString(5, room);
              addOneCourse.setString(6, instrutor);
              var rows = addOneCourse.executeUpdate();
              System.out.println(rows + " row(s) affected.");
            } catch (Exception e) {
              System.err.println("Error occured when adding a course.");
              e.printStackTrace();
            }
            break;

          case "3":
            try {
              var linkStudentAndCourse = conn.prepareStatement("INSERT INTO take(student_id, course_id) VALUES(?, ?)");
              var sid = console.readLine("Student ID: ");
              var cid = console.readLine("Course ID: ");
              linkStudentAndCourse.setString(1, sid);
              linkStudentAndCourse.setString(2, cid);
              var rows = linkStudentAndCourse.executeUpdate();
              System.out.println(rows + " row(s) affected.");
            } catch (Exception e) {
              System.err.println("Error occured when enrolling a student to a course.");
              e.printStackTrace();
            }
            break;

          case "4":
            try {
              var studentsInEachCourse = new StringBuilder(
                  "SELECT course_id, course_name, student_id, CONCAT(student_first_name, ' ', student_last_name) as student_name"
                      + " FROM students NATURAL JOIN take NATURAL JOIN courses");
              var cid = console.readLine("Course ID(Leave blank for all): ");
              if (!cid.isEmpty()) {
                studentsInEachCourse.append(" WHERE course_id = " + cid);
              }
              var rs = sm.executeQuery(studentsInEachCourse.toString());

              var rowCount = 0;
              while (rs.next()) {
                ++rowCount;
                if (rowCount == 1) {
                  System.out.println("course_id, course_name, student_id, student_name");
                }
                System.out
                    .println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));
              }
              if (rowCount == 0) {
                System.out.println("No results.");
              } else {
                System.out.println(rowCount + " row(s) in set.");
              }
            } catch (Exception e) {
              System.err.println("Error occured when querying students in course(s).");
              e.printStackTrace();
            }
            break;

          case "5":
            try {
              var coursesForEachStudent = new StringBuilder(
                  "SELECT student_id, CONCAT(student_first_name, ' ', student_last_name) as student_name, course_id, course_name"
                      + " FROM students NATURAL JOIN take NATURAL JOIN courses");
              var sid = console.readLine("Student ID(Leave blank for all): ");
              if (!sid.isEmpty()) {
                coursesForEachStudent.append(" WHERE student_id = " + sid);
              }
              coursesForEachStudent.append(" ORDER BY student_id ASC");
              var rs = sm.executeQuery(coursesForEachStudent.toString());

              var rowCount = 0;
              while (rs.next()) {
                ++rowCount;
                if (rowCount == 1) {
                  System.out.println("student_id, student_name, course_id, course_name");
                }
                System.out
                    .println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));
              }
              if (rowCount == 0) {
                System.out.println("No results.");
              } else {
                System.out.println(rowCount + " row(s) in set.");
              }
            } catch (Exception e) {
              System.err.println("Error occured when querying courses for student(s).");
              e.printStackTrace();
            }
            break;

          case "6":
            try {
              var sid = console.readLine("Student ID: ");
              var wday = console.readLine("Weekday(ex. Mon): ");

              var coursesForOneStudent = //
                  "SELECT course_id, course_name, course_start_time, course_end_time"
                      + " FROM students NATURAL JOIN take NATURAL JOIN courses" //
                      + " WHERE student_id = " + sid + " AND course_weekday = '" + wday + "'";
              var rs = sm.executeQuery(coursesForOneStudent);

              var rowCount = 0;
              while (rs.next()) {
                ++rowCount;
                if (rowCount == 1) {
                  System.out.println("course_id, course_name, course_start_time, course_end_time");
                }
                System.out
                    .println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4));
              }
              if (rowCount == 0) {
                System.out.println("No results.");
              } else {
                System.out.println(rowCount + " row(s) in set.");
              }
            } catch (Exception e) {
              System.err.println("Error occured when querying courses for a student.");
              e.printStackTrace();
            }
            break;

          case "q":
            return;

          default:
            System.out.println("Unknown option.");
            break;
          }
          option = console.readLine(PROMPT);
        }

      } catch (Exception e) {
        System.err.println(e.getMessage());
        System.err.println("Auth failed. Please retry.");
      }
    }
  }

  static void init(Statement sm) {
    try {
      sm.executeUpdate("CREATE DATABASE IF NOT EXISTS school");

      sm.executeUpdate("CREATE TABLE IF NOT EXISTS `school`.`students` ("//
          + " `student_id` INT NOT NULL AUTO_INCREMENT," //
          + " `student_first_name` VARCHAR(45) NOT NULL,"//
          + " `student_last_name` VARCHAR(45) NOT NULL," //
          + " PRIMARY KEY (`student_id`))");

      sm.executeUpdate("CREATE TABLE IF NOT EXISTS `school`.`courses` ("//
          + " `course_id` INT NOT NULL AUTO_INCREMENT,"//
          + " `course_name` VARCHAR(45) NOT NULL,"//
          + " `course_weekday` VARCHAR(3) NOT NULL,"//
          + " `course_start_time` VARCHAR(5) NOT NULL,"//
          + " `course_end_time` VARCHAR(5) NOT NULL,"//
          + " `course_room` VARCHAR(45) NOT NULL,"//
          + " `course_instrutor` VARCHAR(45) NOT NULL," //
          + " PRIMARY KEY (`course_id`))");

      sm.executeUpdate("CREATE TABLE IF NOT EXISTS `school`.`take` ("//
          + " `student_id` INT NOT NULL,"//
          + " `course_id` INT NOT NULL,"//
          + " PRIMARY KEY (`student_id`, `course_id`),"//
          + " INDEX `fk_course_id_idx` (`course_id` ASC) VISIBLE,"//
          + " CONSTRAINT `fk_student_id`"//
          + "   FOREIGN KEY (`student_id`)"//
          + "   REFERENCES `school`.`students` (`student_id`)"//
          + "   ON DELETE NO ACTION"//
          + "   ON UPDATE NO ACTION,"//
          + " CONSTRAINT `fk_course_id`"//
          + "   FOREIGN KEY (`course_id`)"//
          + "   REFERENCES `school`.`courses` (`course_id`)"//
          + "   ON DELETE NO ACTION"//
          + "   ON UPDATE NO ACTION)");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}