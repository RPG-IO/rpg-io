package io.rpg.model.object;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class GameObjects {
  // this class is mean to only provide static methods
  private GameObjects() {}

  public static Set<String> VALID_TYPES = Set.of("interactive", "navigable");

  public static boolean isValidType(@Nullable String type) {
    return type != null && VALID_TYPES.contains(type);
  }
}
