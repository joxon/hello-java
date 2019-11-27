package uci.mswe.swe245p_gui.ex23_tabs_and_stats;

import java.util.HashMap;
import java.util.HashSet;

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
import uci.mswe.swe245p_gui.ex21_student_roster.Student;
import uci.mswe.swe245p_gui.ex21_student_roster.StudentRoster;

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

    pieData.clear();
    barData.getData().clear();

    var list = app.getStudentList();
    var listSize = list.getSize();
    var set = new HashSet<String>();
    var map = new HashMap<String, Integer>();
    list.forEach(stu -> {
      var major = stu.getMajor();
      set.add(major);

      var grade = stu.getGrade();
      if (map.containsKey(grade)) {
        map.put(grade, map.get(grade) + 1);
      } else {
        map.put(grade, 1);
      }
    });

    if (set.isEmpty()) {
      pieData.add(new PieChart.Data("N/A", 1.0));
    } else {
      set.forEach(major -> {
        var majorSize = list.filtered(stu -> stu.getMajor().equals(major)).size();
        var percentage = String.valueOf((double) majorSize / listSize * 100).split("[.]")[0] + "%";
        var label = major + ", " + majorSize + ", " + percentage;
        pieData.add(new PieChart.Data(label, majorSize));
      });
    }

    if (map.isEmpty()) {
      barData.getData().add(new XYChart.Data<>("N/A", 1));
    } else {
      map.forEach((grade, count) -> {
        barData.getData().add(new XYChart.Data<>(grade, count));
      });
    }

    // ! PieChart has some bugs so we need to re-create it every time
    pie = new PieChart(pieData);
    hBox.getChildren().clear();
    hBox.getChildren().addAll(pie, bar);
  }
}
