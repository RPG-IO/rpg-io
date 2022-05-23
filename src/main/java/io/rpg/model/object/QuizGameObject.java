package io.rpg.model.object;

import io.rpg.model.data.Position;
import org.jetbrains.annotations.NotNull;

public final class QuizGameObject extends InteractiveGameObject {
  public QuizGameObject(@NotNull String tag, @NotNull Position position) {
    super(tag, position);
  }

  @Override
  public void onAction() {
    System.out.println("Quiz object action");
  }
}
