package io.rpg.model.actions;

import io.rpg.Game;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;

public class BattleAction {
    private final GameObject opponent;
    private final int reward;

    public BattleAction(GameObject opponent, int reward) {
        this.opponent = opponent;
        this.reward = reward;
    }

    public GameObject getOpponent() {
        return opponent;
    }

    public int getReward() {
        return reward;
    }
}
