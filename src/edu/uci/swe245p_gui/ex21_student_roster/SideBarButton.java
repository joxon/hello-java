package edu.uci.swe245p_gui.ex21_student_roster;

import javafx.scene.control.Button;

/**
 * SideBarButton
 */
public class SideBarButton extends Button {

  SideBarButton(String text) {
    super(text);
    this.setPrefHeight(60.0);
    this.setPrefWidth(SideBar.MAX_WIDTH);
  }
}
