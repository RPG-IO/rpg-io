package io.rpg.config.model;

import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.GameObjects;
import org.jetbrains.annotations.NotNull;

public class GameObjectConfig extends GameObject {
  private String type;


  public GameObjectConfig(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  public String getType() {
    return type;
  }

  @Override
  public void validate() {
    super.validate();
    if (!GameObjects.isValidType(type)) {
      throw new IllegalStateException("Invalid object type: " + type);
    }
  }
}
