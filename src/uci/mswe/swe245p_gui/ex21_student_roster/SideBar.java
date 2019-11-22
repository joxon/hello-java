package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * SideBar
 */
public class SideBar extends VBox {

  public static final double MAX_WIDTH = 150.0;



  SideBar() {
    var logo = new SideBarLogo();
    var newStudentButton = new SideBarButton("➕ New Student");
    var deleteStudentButton = new SideBarButton("❌ Delete Student");
    var saveChangesButton = new SideBarButton("💾 Save Changes");
    var nextStudentButton = new SideBarButton("⏭️ Next Student");
    var prevStudentButton = new SideBarButton("⏮️ Previous Student");

    this.getChildren().addAll(logo, newStudentButton, deleteStudentButton, saveChangesButton,
        nextStudentButton, prevStudentButton);
    this.setSpacing(10.0); // elem to elem
    this.setPadding(new Insets(10.0)); // elem to border
    this.setMaxWidth(MAX_WIDTH);
    this.setAlignment(Pos.CENTER);
    this.setBackground(Utils.getBackground(Color.DARKGRAY));
  }
}
