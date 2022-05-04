package io.rpg.battle;

import io.rpg.model.actions.Battle;
import io.rpg.model.data.Position;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleTest {

    @Test
    public void playerShouldWinWhenHasMorePoints(){
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject(null, "opponent", null, -2);
        Battle battle = new Battle(player, opponent, 2);

        // When
        String msg = battle.action();

        // Then
        assertEquals("You won! Gained 2 points.", msg);
        assertEquals(2, player.getPoints());
    }

    @Test
    public void playerShouldDrawWhenHasEqualPoints(){
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject(null, "opponent", null, 0);
        Battle battle = new Battle(player, opponent, 2);

        // When
        String msg = battle.action();

        // Then
        assertEquals("Draw.", msg);
        assertEquals(0, player.getPoints());
    }

    @Test
    public void playerShouldLoseWhenHasLessPoints(){
        //Given
        Player player = new Player("p1", new Position(2, 3), "assets/stone.png");
        GameObject opponent = new GameObject(null, "opponent", null, 2);
        Battle battle = new Battle(player, opponent, 2);

        // When
        String msg = battle.action();

        // Then
        assertEquals("You lost :(", msg);
        assertEquals(0, player.getPoints());
    }
}
