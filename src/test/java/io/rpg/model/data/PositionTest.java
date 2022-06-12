package io.rpg.model.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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

  private static Stream<Arguments> northPositions() {
    return Stream.of(
        Arguments.of(new Position(-1, 0)),
        Arguments.of(new Position(-1, 1)),
        Arguments.of(new Position(-10, 0)),
        Arguments.of(new Position(-3, 2)),
        Arguments.of(new Position(-1, -1))
    );
  }
  @ParameterizedTest
  @MethodSource("northPositions")
  void getDirection_shouldReturnNorth(Position north) {
    assertEquals(MapDirection.NORTH, north.getDirection());
  }

  private static Stream<Arguments> southPositions() {
    return Stream.of(
        Arguments.of(new Position(1, 0)),
        Arguments.of(new Position(1, 1)),
        Arguments.of(new Position(10, 0)),
        Arguments.of(new Position(3, 2)),
        Arguments.of(new Position(1, -1))
    );
  }
  @ParameterizedTest
  @MethodSource("southPositions")
  void getDirection_shouldReturnSouth(Position south) {
    assertEquals(MapDirection.SOUTH, south.getDirection());
  }

  private static Stream<Arguments> westPositions() {
    return Stream.of(
        Arguments.of(new Position(0, -1)),
        Arguments.of(new Position(-1, -10)),
        Arguments.of(new Position(9, -10)),
        Arguments.of(new Position(2, -3))
    );
  }
  @ParameterizedTest
  @MethodSource("westPositions")
  void getDirection_shouldReturnWest(Position west) {
    assertEquals(MapDirection.WEST, west.getDirection());
  }

  private static Stream<Arguments> eastPositions() {
    return Stream.of(
        Arguments.of(new Position(0, 1)),
        Arguments.of(new Position(-1, 10)),
        Arguments.of(new Position(9, 10)),
        Arguments.of(new Position(2, 3))
    );
  }
  @ParameterizedTest
  @MethodSource("eastPositions")
  void getDirection_shouldReturnEast(Position east) {
    assertEquals(MapDirection.EAST, east.getDirection());
  }

  @Test
  void getDirection_shouldThrowOnZero() {
    assertThrows(IllegalArgumentException.class, Position.ZERO::getDirection);
  }

  @Test
  void isInside_shouldBeTrue() {
    Position upperBound = new Position(10,10);
    assertTrue(new Position(1,1).isInside(upperBound));
    assertTrue(new Position(1,9).isInside(upperBound));
    assertTrue(new Position(9,1).isInside(upperBound));
    assertTrue(new Position(5,1).isInside(upperBound));
  }

  @Test
  void isInside_shouldBeFalse() {
    Position upperBound = new Position(10,10);
    assertFalse(new Position(-1,1).isInside(upperBound));
    assertFalse(new Position(1,-9).isInside(upperBound));
    assertFalse(new Position(10,1).isInside(upperBound));
    assertFalse(new Position(50,10).isInside(upperBound));
  }
}