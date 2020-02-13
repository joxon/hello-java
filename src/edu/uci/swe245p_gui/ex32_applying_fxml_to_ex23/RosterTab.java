package edu.uci.swe245p_gui.ex32_applying_fxml_to_ex23;

import javafx.scene.control.Tab;

public class RosterTab extends Tab {

  public StudentTable table;

  public StudentForm form;

  public RosterTab() {
    FXMLUtils.loadFXML(this);
  }

  public StudentTable getTable() {
    return table;
  }

  public StudentForm getForm() {
    return form;
  }
}
