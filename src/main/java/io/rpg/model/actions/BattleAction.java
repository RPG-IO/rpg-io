package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;

public final class BattleAction extends BaseAction {
  private final int reward;

  public BattleAction(int reward, Condition condition) {
    super(condition);
    this.reward = reward;
  }

  public GameObject getOpponent() {
    return getEmitter();
  }

  public int getReward() {
    return reward;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
