package uci.mswe.swe245p_gui.ex22_adding_a_table;

import javafx.collections.ListChangeListener;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uci.mswe.swe245p_gui.ex21_student_roster.Student;
import uci.mswe.swe245p_gui.ex21_student_roster.StudentRoster;

/**
 * StudentRosterWithTable
 */
public class StudentRosterWithTable extends StudentRoster {

  private static final int MIN_WINDOW_WIDTH = 1280;
  private static final int MIN_WINDOW_HEIGHT = 720;

  private StudentTable table = new StudentTable(this);

  private VBox center = new VBox();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception {
    super.init();

    this.getStudentList().addListener((ListChangeListener<Student>) e -> System.out.println(e));
    this.getStudentList().addListener((a, b, c) -> System.out.println(a));
    // this.getStudentList().get()
    // .addListener((ListChangeListener<Student>) e -> System.out.println(e));
  }

  @Override
  public void start(Stage stage) throws Exception {
    super.start(stage);

    center.getChildren().addAll(table, this.getStudentForm());
    this.getRoot().setCenter(center);

    stage.setMinHeight(MIN_WINDOW_HEIGHT);
    stage.setMinWidth(MIN_WINDOW_WIDTH);
    stage.setHeight(MIN_WINDOW_HEIGHT);
    stage.setWidth(MIN_WINDOW_WIDTH);

    if (getClass().getSimpleName().equals("StudentRosterWithTable")) {
      stage.show();
    }
  }
}
