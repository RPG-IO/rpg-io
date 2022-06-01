package io.rpg.model.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

class PositionTest {

  @Test
  void neighborhoodIterBasicTest() {
    Position center = new Position(5, 5);
    Position upperBound = new Position(10, 10);
    List<Position> neighbors = List.of(
        new Position(4, 4),
        new Position(4, 5),
        new Position(4, 6),
        new Position(5, 4),
        new Position(5, 6),
        new Position(6, 4),
        new Position(6, 5),
        new Position(6, 6)
    );

    Iterator<Position> expectedIt = neighbors.iterator();
    for (Iterator<Position> it = center.getNeighborhoodIter(upperBound); it.hasNext(); ) {
      Position position = it.next();
      Position expectedPosition = expectedIt.next();
      assertEquals(expectedPosition, position);
    }
  }

  @Test
  void neighborhoodIterUpperBoundTest() {
    Position center = new Position(5, 5);
    Position upperBound = new Position(6, 6);
    List<Position> neighbors = List.of(
        new Position(4, 4),
        new Position(4, 5),
        new Position(5, 4)
    );

    Iterator<Position> expectedIt = neighbors.iterator();
    for (Iterator<Position> it = center.getNeighborhoodIter(upperBound); it.hasNext(); ) {
      Position position = it.next();
      Position expectedPosition = expectedIt.next();
      assertEquals(expectedPosition, position);
    }
  }

  @Test
  void neighborhoodIterLowerBoundTest() {
    Position center = new Position(0, 0);
    Position upperBound = new Position(10, 10);
    List<Position> neighbors = List.of(
        new Position(0, 1),
        new Position(1, 0),
        new Position(1, 1)
    );

    Iterator<Position> expectedIt = neighbors.iterator();
    for (Iterator<Position> it = center.getNeighborhoodIter(upperBound); it.hasNext(); ) {
      Position position = it.next();
      Position expectedPosition = expectedIt.next();
      assertEquals(expectedPosition, position);
    }
  }
}