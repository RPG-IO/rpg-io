package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;

public final class BattleReflexAction extends BaseAction {
  private final int reward;
  private final int requiredStrength;

  public BattleReflexAction(int reward, int requiredStrength, Condition condition) {
    super(condition);
    this.reward = reward;
    this.requiredStrength = requiredStrength;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }

  public int getReward() {
    return reward;
  }

  public int getRequiredStrength() {
    return requiredStrength;
  }
}
