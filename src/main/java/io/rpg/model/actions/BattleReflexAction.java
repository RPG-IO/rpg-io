package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;

public final class BattleReflexAction extends BaseAction {
  private final int reward;

  public BattleReflexAction(int reward, Condition condition) {
    super(condition);
    this.reward = reward;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }

  public int getReward() {
    return reward;
  }

}
