package io.rpg.model.data;

/**
 * TODO.
 */
public class Vector {
  @SuppressWarnings("checkstyle:MemberName")
  public final float x;

  @SuppressWarnings("checkstyle:MemberName")
  public final float y;

  public Vector(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Vector(Position position) {
    // TODO THIS MOTHT
    int SCALE = 64;
    this.x = position.col * SCALE;
    this.y = position.row * SCALE;
  }

}
