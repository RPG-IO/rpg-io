package io.rpg.model.object;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.concurrent.Executors;

// TODO: refactor way of validating {@link GameObject.Type}
public class GameObjects {
  // this class is mean to only provide static methods
  private GameObjects() {}

  public static Set<String> VALID_TYPES = Set.of("navigable", "dialog", "collectible", "player");

  public static boolean isValidType(@Nullable String type) {
    return type != null && VALID_TYPES.contains(type);
  }
}
