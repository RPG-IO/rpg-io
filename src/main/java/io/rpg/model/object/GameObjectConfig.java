package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

public class GameObjectConfig extends GameObject {
  private String type;

  public GameObjectConfig(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  @Override
  public void validate() {
    super.validate();
    if (!GameObjects.isValidType(type)) {
      throw new IllegalStateException("Invalid object type: " + type);
    }
  }
}
