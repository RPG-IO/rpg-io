package io.rpg.model.actions;

public class GameEndAction implements Action {
  public final String description;

  public GameEndAction(String description) {
    this.description = description;
  }
}
