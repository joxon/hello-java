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
package uci.mswe.swe243p.ex51;

import java.sql.*;
import java.util.Scanner;

class Main {

  public static void main(String args[]) {
    final String URL = "jdbc:mysql://localhost:3306/school";
    final String USR = "root";
    String pwd = "";

    var scanner = new Scanner(System.in);
    System.out.println("password:");
    pwd = scanner.nextLine();
    scanner.close();

    try (Connection con = DriverManager.getConnection(URL, USR, pwd)) {
      Class.forName("com.mysql.cj.jdbc.Driver");
      // jdbc:mysql://[host1][:port1][,[host2][:port2]]...[/[database]]
      // [?propertyName1=propertyValue1[&propertyName2=propertyValue2]...]
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from students");
      while (rs.next())
        System.out.println(rs.getInt(1));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}