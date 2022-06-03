package io.rpg.model.actions.condition;

import io.rpg.config.model.ConditionConfigBundle;
import org.jetbrains.annotations.Nullable;

public class ConditionFactory {
  @Nullable
  public static Condition fromConfig(ConditionConfigBundle config) {
    if (config == null) {
      return null;
    }

    // config was validated
    //noinspection ConstantConditions
    switch (config.getType()) {
      case ITEM_REQUIRED -> { return itemRequiredFromConfig(config); }
      default -> throw new IllegalArgumentException("Not implemented condition type: " + config.getType().toString());
    }
  }

  private static ItemRequiredCondition itemRequiredFromConfig(ConditionConfigBundle config) {
    assert config.getItemTag() != null;
    return new ItemRequiredCondition(config.getItemTag());
  }
}
