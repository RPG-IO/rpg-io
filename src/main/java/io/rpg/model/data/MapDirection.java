package io.rpg.model.data;

import javafx.geometry.Point2D;

/**
 * Enum representing directions on the map.
 */
public enum MapDirection {
  NORTH,
  EAST,
  SOUTH,
  WEST;

  /**
   * Returns vector representation of MapDirection in JavaFx coordinate system.

   * @return Point2D
   */
  public Point2D toVector() {
    return switch (this) {
      case EAST -> new Point2D(1, 0);
      case WEST -> new Point2D(-1, 0);
      case NORTH -> new Point2D(0, -1);
      case SOUTH -> new Point2D(0, 1);
    };
  }
}
