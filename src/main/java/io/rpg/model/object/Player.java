package io.rpg.model.object;

import io.rpg.model.data.Position;
import io.rpg.model.data.Vector;
import io.rpg.view.GameObjectView;
import io.rpg.model.object.GameObject;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

public class Player extends GameObject {

  Vector currentPosition;
  int strength;
  float speed;
  Vector direction;
  boolean rightPressed;
  boolean leftPressed;
  boolean upPressed;
  boolean downPressed;
  GameObjectView gameObjectView;
  private Vector pixelPosition;
  private int points;

  //  public GameObject(@NotNull String tag, @NotNull Position position, @NotNull String assetPath) {
//    this.tag = tag;
//    this.position = position;
//    this.assetPath = assetPath;
//  }
  public Player(@NotNull String tag, @NotNull Position position, @NotNull String assetPath) {
    super(tag, position, assetPath);
    this.currentPosition=new Vector(position.col, position.row);
    this.speed = 100f;
    this.direction = new Vector(0, 0);
    this.rightPressed = false;
    this.leftPressed = false;
    this.upPressed = false;
    this.downPressed = false;
    this.strength = 0;
    this.pixelPosition = new Vector(position);
    this.points = 0;
  }

  public void updateStrength(int value) {
    strength += value;
  }

  public void setDirection(Vector direction) {
    this.direction = direction;
  }

  public Vector getPixelPosition() {
    return pixelPosition;
  }

  public void update(float elapsed) {
    float y = 0;
    float x = 0;
//        the sum tells us the direction
    if (upPressed)
      y += -1;

    if (downPressed)
      y += 1;

    if (leftPressed)
      x += -1;

    if (rightPressed)
      x += 1;

    this.pixelPosition = new Vector(this.pixelPosition.x + speed * x * elapsed / 1000, this.pixelPosition.y + speed * y * elapsed / 1000);
  }

  public void setRightPressed(boolean rightPressed) {
    this.rightPressed = rightPressed;
  }

  public void setLeftPressed(boolean leftPressed) {
    this.leftPressed = leftPressed;
  }

  public void setUpPressed(boolean upPressed) {
    this.upPressed = upPressed;
  }

  public void setDownPressed(boolean downPressed) {
    this.downPressed = downPressed;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public void setGameObjectView(GameObjectView gameObjectView) {
    this.gameObjectView = gameObjectView;
  }

  public void render() {
    if (gameObjectView!=null) {
      gameObjectView.setX(this.pixelPosition.x);
      gameObjectView.setY(this.pixelPosition.y);
    }
  }

  public int getPoints() {
    return points;
  }

  public void addPoints(int value) {
    points += value;
  }
}
