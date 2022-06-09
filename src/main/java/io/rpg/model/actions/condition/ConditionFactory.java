package io.rpg.model.actions.condition;

import io.rpg.config.model.ConditionConfigBundle;
import org.jetbrains.annotations.Nullable;

public final class ConditionFactory {
  @Nullable
  public static Condition fromConfig(ConditionConfigBundle config) {
    if (config == null) {
      return null;
    }

    // config was validated
    //noinspection ConstantConditions
    switch (config.getType()) {
      case ITEM_REQUIRED -> { return itemRequiredFromConfig(config); }
      case DEFEAT_OPPONENT -> { return defeatOpponentFromConfig(config); }
      case LEVEL_REQUIRED -> { return levelRequiredFromConfig(config); }
      case POINTS_REQUIRED -> { return pointsRequiredFromConfig(config); }
      default -> throw new IllegalArgumentException("Not implemented condition type: " + config.getType().toString());
    }
  }

  private static ItemRequiredCondition itemRequiredFromConfig(ConditionConfigBundle config) {
    assert config.getObjectTag() != null;
    return new ItemRequiredCondition(config.getObjectTag());
  }

  private static DefeatOpponentCondition defeatOpponentFromConfig(ConditionConfigBundle config) {
    assert config.getObjectTag() != null;
    return new DefeatOpponentCondition(config.getObjectTag());
  }

  private static LevelRequiredCondition levelRequiredFromConfig(ConditionConfigBundle config) {
    assert config.getRequiredLevel() != null;
    return new LevelRequiredCondition(config.getRequiredLevel());
  }

  private static PointsRequiredCondition pointsRequiredFromConfig(ConditionConfigBundle config) {
    assert config.getRequiredPoints() != null;
    return new PointsRequiredCondition(config.getRequiredPoints());
  }
}
