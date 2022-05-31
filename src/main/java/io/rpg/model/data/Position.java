package io.rpg.model.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Consumer;
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

  public Iterator<Position> getNeighborhoodIter(Position upperBound) {
    return new NeighborhoodIter(this, upperBound);
  }

  private static class NeighborhoodIter implements Iterator<Position> {
    private final ListIterator<Position> iterator;

    private NeighborhoodIter(Position center, Position upperBound) {
      List<Position> positions = new ArrayList<>(8);
      for (int i = -1; i < 2; i++) {
        for (int j = -1; j < 2; j++) {
          int col = center.col + i;
          int row = center.row + j;

          if (col < 0 || row < 0) continue;
          if (col >= upperBound.col || row >= upperBound.row) continue;
          if (i == 0 && j == 0) continue;

          positions.add(new Position(col, row));
        }
      }

      iterator = positions.listIterator();
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public Position next() {
      return iterator.next();
    }

    @Override
    public void remove() {
      iterator.remove();
    }

    @Override
    public void forEachRemaining(Consumer<? super Position> action) {
      iterator.forEachRemaining(action);
    }
  }

}
