package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.Nullable;

public abstract class BaseAction implements Action {
  @Nullable
  private final Condition condition;

  @Nullable
  private GameObject emitter;

  @Nullable
  private Action afterAction;

  @Nullable
  private Action beforeAction;

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

  @Override
  public void setAfterAction(Action action) {
    afterAction = action;
  }

  @Override
  public void setBeforeAction(Action action) {
    beforeAction = action;
  }

  @Override
  @Nullable
  public Action getBeforeAction() {
    return beforeAction;
  }

  @Override
  @Nullable
  public Action getAfterAction() {
    return afterAction;
  }
}
