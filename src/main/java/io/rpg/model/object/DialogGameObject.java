package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

public final class DialogGameObject extends InteractiveGameObject {
  public DialogGameObject(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  @Override
  public void onAction() {
    System.out.println("Dialogue object action");
  }
}
