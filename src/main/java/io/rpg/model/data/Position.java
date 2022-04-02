package io.rpg.model.data;

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
}
