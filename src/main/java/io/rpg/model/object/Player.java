package io.rpg.model.object;

import io.rpg.model.data.Position;
import io.rpg.view.GameObjectView;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;

public class Player extends GameObject {

  private int strength;
  private float speed;
  private boolean rightPressed;
  private boolean leftPressed;
  private boolean upPressed;
  private boolean downPressed;
  private GameObjectView gameObjectView;
  private Point2D pixelPosition;


  public Player(@NotNull String tag, @NotNull Position position, @NotNull String assetPath) {
    super(tag, position);
    this.speed = 6f;
    this.rightPressed = false;
    this.leftPressed = false;
    this.upPressed = false;
    this.downPressed = false;
    this.strength = 0;
    this.pixelPosition = new Point2D(position.col, position.row);
  }

  public void updateStrength(int value) {
    strength += value;
  }


  public Point2D getPixelPosition() {
    return pixelPosition;
  }

  public void update(float elapsed) {
    float y = 0;
    float x = 0;
//        the sum tells us the direction
    if (upPressed) {
      y += -1;
    }

    if (downPressed) {
      y += 1;
    }

    if (leftPressed) {
      x += -1;
    }

    if (rightPressed) {
      x += 1;
    }

    Point2D nextPosition = new Point2D(x, y)
        .multiply(speed * elapsed / 1000)
        .add(this.pixelPosition);
    this.pixelPosition = nextPosition;
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
    if (gameObjectView != null) {
      gameObjectView.setX(this.pixelPosition.getX());
      gameObjectView.setY(this.pixelPosition.getY());
    }
  }

  public void setPosition(Position position) {
    pixelPosition = new Point2D(position.col, position.row);
  }
}
