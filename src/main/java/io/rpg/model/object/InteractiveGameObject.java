package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

public abstract class InteractiveGameObject extends GameObject {
  public InteractiveGameObject(@NotNull String tag, @NotNull Position position, String assetPath) {
    super(tag, position);
  }
  public InteractiveGameObject(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  abstract public void onAction();
}
