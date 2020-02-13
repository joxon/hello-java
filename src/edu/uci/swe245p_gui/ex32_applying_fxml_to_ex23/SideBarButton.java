package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;

public class SideBarButton extends Button {
  public SideBarButton(@NamedArg("text") String text) {
    super(text);
    FXMLUtils.loadFXML(this);

    this.setPrefHeight(60.0);
    this.setPrefWidth(SideBar.MAX_WIDTH);
  }
}
