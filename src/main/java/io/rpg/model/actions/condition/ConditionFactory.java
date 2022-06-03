package io.rpg.model.actions.condition;

import io.rpg.config.model.ConditionConfigBundle;

public class ConditionFactory {
  public static Condition fromConfig(ConditionConfigBundle config) {
    // config was validated
    //noinspection ConstantConditions
    switch (config.getType()) {
      case ITEM_REQUIRED -> { return itemRequiredFromConfig(config); }
      default -> throw new IllegalArgumentException("Not implemented condition type: " + config.getType().toString());
    }
  }

  private static ItemRequiredCondition itemRequiredFromConfig(ConditionConfigBundle config) {
    return new ItemRequiredCondition();
  }
}
