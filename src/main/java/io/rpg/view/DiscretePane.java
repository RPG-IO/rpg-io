package io.rpg.view;

import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class DiscretePane extends Pane {
  private static final double FIELD_SIZE = 64;

  @Override
  protected void layoutChildren() {
    List<Node> managed = getManagedChildren();
    Insets insets = getInsets();
    Pos align = Pos.CENTER;
    double top = snapSpaceY(insets.getTop());
    double left = snapSpaceX(insets.getLeft());




    double x = left + FIELD_SIZE;
    double y = top;
    double baselineOffset = -1;

    for (int i = 0, size = managed.size(); i < size; i++) {
      Node child = managed.get(i);
      child.maxWidth(FIELD_SIZE);
      child.maxHeight(FIELD_SIZE);
      if (child instanceof ImageView imageView){
        imageView.setFitWidth(FIELD_SIZE);
        imageView.setFitHeight(FIELD_SIZE);
        layoutInArea(child,imageView.getX() * FIELD_SIZE , imageView.getY() * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE,
            baselineOffset,
            HPos.LEFT, VPos.TOP);
      }

    }

  }
}
