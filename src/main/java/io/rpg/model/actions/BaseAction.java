package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseAction implements Action {
  @Nullable
  private final Condition condition;

  @Nullable
  private GameObject emitter;

  public BaseAction(@Nullable Condition condition) {
    this.condition = condition;
  }

  @Nullable
  public Condition getCondition() {
    return condition;
  }

  public void setEmitter(GameObject emitter) {
    this.emitter = emitter;
  }

  @Nullable
  public GameObject getEmitter() {
    return emitter;
  }
}
