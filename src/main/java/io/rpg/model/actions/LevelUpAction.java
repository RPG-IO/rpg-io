package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;

/**
 * Class for storing local data to perform level up action
 */
public class LevelUpAction extends BaseAction {
  public final int newLevel;
  private final Integer points;

  public LevelUpAction(int newLevel, Integer points, Condition condition) {
    super(condition);
    this.newLevel = newLevel;
    this.points = points;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }

  public Integer getPoints() {
    return points;
  }
}
