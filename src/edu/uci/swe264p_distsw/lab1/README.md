# SWE 264P Lab 1: Pipe-and-Filter

## Info

- Author: Junxian Chen
- Date: Jan. 27, 2020

## Setup

1. This is an IntelliJ IDEA project. Open this project with IntelliJ IDEA and version 2019.3 is recommended.
2. You may need to setup JDK after opening the project. Navigate to "File" -> "Project Structure". In the dialog select a Project SDK from the combo box (or drop-down list). Please be advised JDK version 13 is adopted for development and thus recommended. Then click "Apply".
3. All input and output files are placed under `\data\swe264p_lab1`. If you want to test the systems with different input files it is recommended to place them into `\data\swe264p_lab1` first, then open `SourceFilter.java` located in `src\uci\mswe\swe264p_distsw\lab1\system_a` or `src\uci\mswe\swe264p_distsw\lab1\system_b`, change

   ```java

   String fileName = "data/swe264p_lab1/FlightData.dat";

   ```

   to

   ```java

   String fileName = "data/swe264p_lab1/your_input_file";

   ```

4. To run the programs, you may right-click the `Plumber.java` located in `src\uci\mswe\swe264p_distsw\lab1\system_a` or `src\uci\mswe\swe264p_distsw\lab1\system_b`, and select `Run Plumber - System A` or `Run Plumber - System B` in the menu. Or you may select a configuration from the combo box on the top-right side, then click the green arrow next to it (Run). You should see output in the console and csv files being generated under `\data\swe264p_lab1`.
