package io.rpg.model.actions;

import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;

public class Battle {
    private final Player player;
    private final GameObject opponent;
    private final int reward;

    public Battle(Player player, GameObject opponent, int reward) {
        this.player = player;
        this.opponent = opponent;
        this.reward = reward;
    }

    public String action(){
        if(player.getStrength() > opponent.getStrength()){
            player.addPoints(reward);
            return "You won! Gained " + reward + " points.";
        }else if(player.getStrength() < opponent.getStrength()){
            return "You lost :(";
        }
        return "Draw.";
    }
}
