package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;

public class BattleAction extends ConditionalAction {
  private GameObject opponent;
  private final int reward;

  public BattleAction(int reward, Condition condition) {
    super(condition);
    this.reward = reward;
  }

  public void setOpponent(GameObject object) {
    this.opponent = object;
  }

  public GameObject getOpponent() {
    return opponent;
  }

  public int getReward() {
    return reward;
  }
}
