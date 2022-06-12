package io.rpg.model.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

/**
 * Represents current position by holding row / col values.
 * This class can NOT be record due to some issues with
 * Gson library.
 */
public final class Position {
  public static final Position ZERO = new Position(0, 0);
  public final int row;
  public final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public boolean isInside(Position lowerLeft, Position upperRight) {
    return lowerLeft.col <= this.col && lowerLeft.row <= this.row
        && upperRight.col > this.col && upperRight.row > this.row;
  }

  public boolean isInside(Position upperLeft) {
    return isInside(ZERO, upperLeft);
  }

  public Position(Point2D point2D) {
    this((int) Math.round(point2D.getY()), (int) Math.round(point2D.getX()));
  }

  public Position subtract(@NotNull Position other) {
    return new Position(row - other.row, col - other.row);
  }

  /**
   * Calculates most fitting MapDirection with priority on NORTH, SOUTH directions.

   * @return MapDirection.
   */
  public MapDirection getDirection() {
    if (this.equals(ZERO)) {
      throw new IllegalArgumentException("Direction of ZERO is undefined");
    }

    if (Math.abs(col) > Math.abs(row)) {
      if (col > 0) {
        return MapDirection.EAST;
      }
      return MapDirection.WEST;
    }

    if (row > 0) {
      return MapDirection.SOUTH;
    }

    return MapDirection.NORTH;
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
          int col = center.col + j;
          int row = center.row + i;

          if (col < 0 || row < 0) continue;
          if (col >= upperBound.col || row >= upperBound.row) continue;
          if (i == 0 && j == 0) continue;

          positions.add(new Position(row, col));
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

  public Iterator<Position> getBfsIter(Position upperBound) {
    return new FourNeighborBfsIter(this, upperBound);
  }

  private static class FourNeighborBfsIter implements Iterator<Position> {
    private final Set<Position> visited;
    private final Queue<Position> queue;
    private final Position upperBound;

    public FourNeighborBfsIter(Position start, Position upperBound) {
      this.upperBound = upperBound;
      visited = new HashSet<>();
      queue = new LinkedList<>();
      queue.add(start);
      visited.add(start);
    }

    @Override
    public boolean hasNext() {
      return !queue.isEmpty();
    }

    private void addToQueueIfValid(Position position) {
      if (position.isInside(ZERO, upperBound) && !visited.contains(position)) {
        queue.add(position);
        visited.add(position);
      }
    }

    @Override
    public Position next() {
      Position position = queue.poll();
      assert position != null;
      addToQueueIfValid(new Position(position.row + 1, position.col));
      addToQueueIfValid(new Position(position.row, position.col + 1));
      addToQueueIfValid(new Position(position.row, position.col - 1));
      addToQueueIfValid(new Position(position.row - 1, position.col));

      return position;
    }
  }

}
