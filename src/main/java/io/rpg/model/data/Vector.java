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
    this.x = position.col * 32;
    this.y = position.row * 32;
  }

}
