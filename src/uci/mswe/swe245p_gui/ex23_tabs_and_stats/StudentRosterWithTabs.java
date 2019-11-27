package uci.mswe.swe245p_gui.ex23_tabs_and_stats;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import uci.mswe.swe245p_gui.ex22_adding_a_table.StudentRosterWithTable;

/**
 * StudentRosterWithTabs
 */
public class StudentRosterWithTabs extends StudentRosterWithTable {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);

        var tabPane = new TabPane();
        tabPane.getTabs().addAll(new RosterTab(this), new StatsTab(this));
        this.getRoot().setCenter(tabPane);

        if (getClass().getSimpleName().equals("StudentRosterWithTabs")) {
            stage.show();
        }
    }
}
