# SWE 264P Lab 2: Implicit-Invocation System

## Info

- Author: Junxian Chen
- Date: Feb. 02, 2020

## Setup

1. This is an IntelliJ IDEA project. Open this project with IntelliJ IDEA and version 2019.3 is recommended.
2. You may need to setup JDK after opening the project. Navigate to "File" -> "Project Structure". In the dialog select a Project SDK from the combo box (or drop-down list). Please be advised JDK version 13 is adopted for development and thus recommended. Then click "Apply".
3. All input and output files are placed under `\data\swe264p_lab2`. If you want to test the systems with different input files it is recommended to place them into `\data\swe264p_lab2` first, then open `SystemMain.java` located in `src\uci\mswe\swe264p_distsw\lab2\mod\`, change

   ```java

    if (args.length == 2) {
      studentFileName = args[0];
      courseFileName = args[1];
    } else {
      studentFileName = "data/swe264p_lab2/Students.txt";
      courseFileName = "data/swe264p_lab2/Courses.txt";
    }

   ```

   to

   ```java

    if (args.length == 2) {
      studentFileName = args[0];
      courseFileName = args[1];
    } else {
      studentFileName = "data/swe264p_lab2/Students_File_Path";
      courseFileName = "data/swe264p_lab2/Courses_File_Path";
    }

   ```

4. To run the programs, you may right-click the `SystemMain.java` located in `src\uci\mswe\swe264p_distsw\lab2\mod\`, and select `Run SystemMain.main()` in the menu. Or you may select a configuration from the combo box on the top-right side, then click the green arrow next to it (Run). You should see output in the console and log files being generated under `\data\swe264p_lab2\`.

## How to test the "Overbooked" feature

Start the program. Then type the following inputs:

  ```text
  6
  G00123456
  CS112
  a

  6
  G00123432
  CS112
  a

  6
  G45643133
  CS112
  a

  6
  G01234567
  CS112
  a
  ```

The goal is to register a class with more than 3 students. Then we expect the following outputs:

  ```text
  Successful! (Overbooked! Currently 4)
  ```
