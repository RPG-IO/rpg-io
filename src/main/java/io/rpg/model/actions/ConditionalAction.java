package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import org.jetbrains.annotations.Nullable;

public abstract class ConditionalAction implements Action {
  @Nullable
  private final Condition condition;

  public ConditionalAction(@Nullable Condition condition) {
    this.condition = condition;
  }

  @Nullable
  public Condition getCondition() {
    return condition;
  }
}
