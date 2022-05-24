package io.rpg.model.actions;

import io.rpg.model.object.GameObject;

public class BattleAction implements Action {
  private GameObject opponent;
  private final int reward;

  public BattleAction(int reward) {
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
