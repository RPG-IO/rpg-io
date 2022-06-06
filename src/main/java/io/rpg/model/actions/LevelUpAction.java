package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;

/**
 * Class for storing local data to perform level up action
 */
public class LevelUpAction extends BaseAction {
  public final int newLevel;

  public LevelUpAction(int newLevel, Condition condition) {
    super(condition);
    this.newLevel = newLevel;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
