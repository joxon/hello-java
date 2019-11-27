package uci.mswe.swe245p_gui.ex21_student_roster;

import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * SideBarLogo
 */
public class SideBarLogo extends StackPane {

  SideBarLogo() {

    // • A product logo (drawn, not from an image file) that uses
    // ○ shadow,
    // ○ light, and
    // ○ reflection effects.
    // Put it anywhere appropriate on your UI.

    // shadow
    var shadow = new InnerShadow(15, Color.BLACK);

    var circle = new Circle(80);
    circle.setFill(Color.WHITE);
    circle.setStroke(Color.BLACK);
    circle.setStrokeWidth(4);
    circle.setEffect(shadow);

    // light
    var light = new Light.Distant();
    light.setAzimuth(-135);

    var lighting = new Lighting();
    lighting.setLight(light);
    lighting.setSurfaceScale(5);

    var text = new Text("StuR");
    text.setFill(Color.SLATEGRAY);
    text.setFont(Font.font(null, FontWeight.BOLD, 60));
    text.setEffect(lighting);

    // reflection
    var reflection = new Reflection();
    reflection.setFraction(0.05);
    this.setEffect(reflection);

    this.getChildren().addAll(circle, text);
    this.setMaxSize(0, 0); // compact
  }
}
