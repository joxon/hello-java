package uci.mswe.swe245p_gui.ex23_tabs_and_stats;

import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import uci.mswe.swe245p_gui.ex22_adding_a_table.StudentRosterWithTable;

public class RosterTab extends Tab {

    StudentRosterWithTable app;

    RosterTab(StudentRosterWithTable app) {
        this.app = app;

        var vbox = new VBox();
        vbox.getChildren().addAll(app.getStudentTable(), app.getStudentForm());
        this.setContent(vbox);

        this.setClosable(false);
        this.setText("Roster");
    }
}
