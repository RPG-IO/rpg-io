package io.rpg.model.data;

import java.util.Objects;

// This class can NOT be record due to some issues
// with Gson library
public class Position {
  public final int row;

  public final int col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
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
    } else if (!(obj instanceof Position)){
      return false;
    } else {
      Position that = (Position) obj;
      return this.row == that.row && this.col == that.col;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }
}