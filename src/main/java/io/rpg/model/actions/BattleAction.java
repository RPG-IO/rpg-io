package io.rpg.model.actions;

import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;

public class BattleAction {
    private final Player player;
    private final GameObject opponent;
    private final int reward;

    public BattleAction(Player player, GameObject opponent, int reward) {
        this.player = player;
        this.opponent = opponent;
        this.reward = reward;
    }

    public Player getPlayer() {
        return player;
    }

    public GameObject getOpponent() {
        return opponent;
    }

    public int getReward() {
        return reward;
    }
}
