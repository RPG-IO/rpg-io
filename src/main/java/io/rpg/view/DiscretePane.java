package io.rpg.view;

import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Custom pane. It scales children to correct size and lays them in correct spot.
 */
public class DiscretePane extends Pane {
  private static final double FIELD_SIZE = 64;

  /**
   * Creates a DiscretePane.
   */
  public DiscretePane() {
    super();
    getChildren().addListener((ListChangeListener<Node>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          c.getAddedSubList().forEach(this::modifyNewChild);
        }
      }
    });
  }

  private void modifyNewChild(Node node) {
    node.maxWidth(FIELD_SIZE);
    node.maxHeight(FIELD_SIZE);
    if (node instanceof ImageView imageView) {
      imageView.setFitWidth(FIELD_SIZE);
      imageView.setFitHeight(FIELD_SIZE);
    }
  }


  @Override
  protected void layoutChildren() {
    double baselineOffset = 0;
    for (Node child : getManagedChildren()) {

      Point2D position = calcLayoutPosition(child);

      layoutInArea(child,
          position.getX(),
          position.getY(),
          FIELD_SIZE,
          FIELD_SIZE,
          baselineOffset,
          HPos.LEFT,
          VPos.TOP);
    }
  }

  private Point2D calcLayoutPosition(Node child) {
    double x;
    double y;

    if (child instanceof GameObjectView gameObjectView) {
      return gameObjectView.getPosition().multiply(FIELD_SIZE);
    } else {
      x = child.getLayoutX();
      y = child.getLayoutY();
    }

    return new Point2D(x, y);
  }
}
