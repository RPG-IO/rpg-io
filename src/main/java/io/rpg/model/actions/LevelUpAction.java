package io.rpg.model.actions;

/**
 * Class for storing local data to perform level up action
 */
public class LevelUpAction implements Action {
  public final int newLevel;

  public LevelUpAction(int newLevel) {
    this.newLevel = newLevel;
  }
}
