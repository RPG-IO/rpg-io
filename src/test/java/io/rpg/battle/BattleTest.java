package io.rpg.battle;

import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import io.rpg.util.BattleResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleTest {

    @Test
    public void playerShouldWinWhenHasMorePoints() {
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject("opponent", new Position(2, 5), -2);
        int reward = 2;

        // When
        BattleResult result;
        if (player.getStrength() > opponent.getStrength()) {
            player.addPoints(reward);
            result = new BattleResult(BattleResult.Result.VICTORY, reward);
        } else if (player.getStrength() < opponent.getStrength()) {
            result = new BattleResult(BattleResult.Result.DEFEAT, 0);
        } else {
            result = new BattleResult(BattleResult.Result.DRAW, 0);
        }

        // Then
        assertEquals("You won! Gained 2 points.", result.getMessage());
        assertEquals(2, player.getPoints());
    }

    @Test
    public void playerShouldDrawWhenHasEqualPoints() {
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject("opponent", new Position(2, 5), 0);
        int reward = 2;

        // When
        BattleResult result;
        if (player.getStrength() > opponent.getStrength()) {
            player.addPoints(reward);
            result = new BattleResult(BattleResult.Result.VICTORY, reward);
        } else if (player.getStrength() < opponent.getStrength()) {
            result = new BattleResult(BattleResult.Result.DEFEAT, 0);
        } else {
            result = new BattleResult(BattleResult.Result.DRAW, 0);
        }

        // Then
        assertEquals("Draw.", result.getMessage());
        assertEquals(0, player.getPoints());
    }

    @Test
    public void playerShouldLoseWhenHasLessPoints() {
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject("opponent", new Position(2, 5), 2);
        int reward = 2;

        // When
        BattleResult result;
        if (player.getStrength() > opponent.getStrength()) {
            player.addPoints(reward);
            result = new BattleResult(BattleResult.Result.VICTORY, reward);
        } else if (player.getStrength() < opponent.getStrength()) {
            result = new BattleResult(BattleResult.Result.DEFEAT, 0);
        } else {
            result = new BattleResult(BattleResult.Result.DRAW, 0);
        }

        // Then
        assertEquals("You lost :(", result.getMessage());
        assertEquals(0, player.getPoints());
    }
}