package io.rpg.model.actions;

import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import io.rpg.util.BattleResult;

public class Battle {
    private final Player player;
    private final GameObject opponent;
    private final int reward;

    public Battle(Player player, GameObject opponent, int reward) {
        this.player = player;
        this.opponent = opponent;
        this.reward = reward;
    }

    public BattleResult action() {
        if (player.getStrength() > opponent.getStrength()) {
            player.addPoints(reward);
            return new BattleResult(BattleResult.Result.VICTORY, reward);
        } else if (player.getStrength() < opponent.getStrength()) {
            return new BattleResult(BattleResult.Result.DEFEAT, 0);
        }
        return new BattleResult(BattleResult.Result.DRAW, 0);
    }
}
