package io.rpg.model.actions;

import java.util.Optional;

public enum ActionType {
  Quiz("quiz"),
  GameEnd("game-end"),
  LocationChange("location-change"),
  ShowDescription("show-description"),
  Dialogue("dialogue");

  private final String asString;

  ActionType(String string) {
    asString = string;
  }

  public String toString() {
    return asString;
  }

  public static Optional<ActionType> fromString(String action) {
    switch (action) {
      case "quiz" -> { return Optional.of(Quiz); }
      case "game-end" -> { return Optional.of(GameEnd); }
      case "location-change" -> { return Optional.of(LocationChange); }
      case "show-description" -> { return Optional.of(ShowDescription); }
      case "dialogue" -> { return Optional.of(Dialogue); }
      default -> { return Optional.empty(); }
    }
  }
}
