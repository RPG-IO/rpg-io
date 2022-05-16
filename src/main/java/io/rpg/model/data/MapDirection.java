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
   * Returns MapDirection corresponding to given vector.
   * Direction is calculated using javafx coordinate system.

   * @param v Vector with one coordinate equal to 0 and another equal to 1 or -1.
   * @return MapDirection.
   */

  public static MapDirection fromDirectionVector(Point2D v) {
    v = v.normalize();

    if (v.getX() == 0) {
      if (v.getY() == -1) {
        return NORTH;
      } else if (v.getY() == 1) {
        return SOUTH;
      } else {
        throw new IllegalArgumentException("Vector is equal to [0, 0]");
      }
    } else {
      if (v.getX() == 1) {
        return EAST;
      } else {
        return WEST;
      }
    }
  }
}
