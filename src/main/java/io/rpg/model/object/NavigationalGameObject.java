package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

public final class NavigationalGameObject extends InteractiveGameObject {
  public NavigationalGameObject(@NotNull String tag,
                                @NotNull Position position) {
    super(tag, position);
  }

  public void navigateTo(Object target) {
  }

  @Override
  public void onAction() {

  }
}
