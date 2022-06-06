package io.rpg.model.actions.condition;

import io.rpg.model.actions.ConditionType;

public final class LevelRequiredCondition extends Condition {

  private final int level;

  public LevelRequiredCondition(int requiredLevel) {
    super(ConditionType.LEVEL_REQUIRED);
    this.level = requiredLevel;
  }

  public int getLevel() {
    return level;
  }

  @Override
  public boolean acceptEngine(ConditionEngine engine) {
    return engine.evaluate(this);
  }
}
