package edu.uci.swe245p_gui.ex21_student_roster;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Utils
 */
public class Utils {

  public static Background getBackground(final Color color) {
    return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
  }
}
