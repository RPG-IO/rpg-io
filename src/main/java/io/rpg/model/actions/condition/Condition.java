package io.rpg.model.actions.condition;

import io.rpg.model.actions.ConditionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Condition {

  @NotNull
  private final ConditionType type;

  public Condition(@NotNull ConditionType type) {
    //noinspection ConstantConditions
    assert type != null;
    this.type = type;
  }

  @NotNull
  public ConditionType getType() {
    return type;
  }

  public abstract boolean acceptEngine(final ConditionEngine engine);
}
