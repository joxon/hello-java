package uci.mswe.swe245p_gui.ex32_applying_fxml_to_ex23;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;

public class StatsTab extends Tab implements Initializable {

  StudentRoster app;

  private ObservableList<PieChart.Data> pieData;
  private XYChart.Series<String, Number> barData;

  public PieChart pie;
  public BarChart<String, Number> bar;

  private HBox hBox;

  public StatsTab() {
    this.app = StudentRoster.getApp();
    FXMLUtils.loadFXML(this);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
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
