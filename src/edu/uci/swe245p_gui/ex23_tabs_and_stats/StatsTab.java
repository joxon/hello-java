package edu.uci.swe245p_gui.ex23_tabs_and_stats;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import edu.uci.swe245p_gui.ex21_student_roster.Student;
import edu.uci.swe245p_gui.ex21_student_roster.StudentRoster;

public class StatsTab extends Tab {

  StudentRoster app;

  private ObservableList<PieChart.Data> pieData;
  private XYChart.Series<String, Number> barData;

  private PieChart pie;
  private BarChart<String, Number> bar;

  private HBox hBox;

  StatsTab(StudentRoster app) {
    this.app = app;

    pieData = FXCollections.observableArrayList(new PieChart.Data("N/A", 1));
    pie = new PieChart(pieData);
    pie.setTitle("Major Distribution");

    barData = new XYChart.Series<>();
    var xAxis = new CategoryAxis();
    xAxis.setLabel("Letter Grade");
    var yAxis = new NumberAxis();
    yAxis.setLabel("Frequencies");
    bar = new BarChart<>(xAxis, yAxis);
    bar.setTitle("Frequencies of each letter grade");
    bar.getData().add(barData);

    hBox = new HBox(pie, bar);
    this.setContent(hBox);
    this.setClosable(false);
    this.setText("Stats");

    this.app.getStudentList().addListener((ListChangeListener<Student>) change -> {
      refreshCharts();
    });
    this.setOnSelectionChanged(change -> {
      refreshCharts();
    });

  }

  private void refreshCharts() {

    // pieData.clear();
    pieData = FXCollections.observableArrayList();
    barData.getData().clear();

    var list = app.getStudentList();
    var listSize = list.getSize();
    var majorMap = new HashMap<String, Integer>();
    var gradeMap = new HashMap<String, Integer>();
    list.forEach(stu -> {
      var major = stu.getMajor();
      if (majorMap.containsKey(major)) {
        majorMap.put(major, majorMap.get(major) + 1);
      } else {
        majorMap.put(major, 1);
      }

      var grade = stu.getGrade();
      if (gradeMap.containsKey(grade)) {
        gradeMap.put(grade, gradeMap.get(grade) + 1);
      } else {
        gradeMap.put(grade, 1);
      }
    });

    if (majorMap.isEmpty()) {
      pieData.add(new PieChart.Data("N/A", 1.0));
    } else {
      majorMap.forEach((major, count) -> {
        var percentage = String.valueOf((double) count / listSize * 100).split("[.]")[0] + "%";
        var label = major + ", " + count + ", " + percentage;
        pieData.add(new PieChart.Data(label, count));
      });
    }

    if (gradeMap.isEmpty()) {
      barData.getData().add(new XYChart.Data<>("N/A", 1));
    } else {
      gradeMap.forEach((grade, count) -> {
        barData.getData().add(new XYChart.Data<>(grade, count));
      });
    }

    // ! PieChart has some bugs so we need to re-create it every time
    pie = new PieChart(pieData);
    pie.setTitle("Major Distribution");
    hBox.getChildren().set(0, pie);

    // hBox.getChildren().clear();
    // hBox.getChildren().addAll(pie, bar);
  }
}
