package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;

public class GameEndAction extends BaseAction {
  public final String description;

  public GameEndAction(String description, Condition condition) {
    super(condition);
    this.description = description;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
