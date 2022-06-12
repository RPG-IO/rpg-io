package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;

public final class BattleAction extends BaseAction {
  private final int reward;
  private final int requriedStrength;

  public BattleAction(int reward, int requiredStrength, Condition condition) {
    super(condition);
    this.reward = reward;
    this.requriedStrength = requiredStrength;
  }

  public GameObject getOpponent() {
    return getEmitter();
  }

  public int getReward() {
    return reward;
  }

  public int getRequriedStrength() {
    return requriedStrength;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
