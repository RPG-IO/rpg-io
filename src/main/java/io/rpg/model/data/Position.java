package io.rpg.model.data;

import java.util.Objects;
import javafx.geometry.Point2D;

/**
 * Represents current position by holding row / col values.
 * This class can NOT be record due to some issues with
 * Gson library.
 */
public final class Position {
  public final int row;
  public final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Position(Point2D point2D) {
    this((int) Math.round(point2D.getY()), (int) Math.round(point2D.getX()));
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof Position that)) {
      return false;
    } else {
      return this.row == that.row && this.col == that.col;
    }
  }

  @Override
  public String toString() {
    return "{ row: " + row + ", col: " + col + " }";
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}
