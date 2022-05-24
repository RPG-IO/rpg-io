package io.rpg.model.actions;

import com.google.gson.annotations.SerializedName;

import java.util.Optional;

public enum ActionType {
  @SerializedName("quiz")
  Quiz("quiz"),

  @SerializedName("game-end")
  GameEnd("game-end"),

  @SerializedName("location-change")
  LocationChange("location-change"),

  @SerializedName("show-description")
  ShowDescription("show-description"),

  @SerializedName("dialogue")
  Dialogue("dialogue"),

  @SerializedName("battle")
  Battle("battle");

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
      case "battle" -> { return Optional.of(Battle); }
      default -> { return Optional.empty(); }
    }
  }
}
