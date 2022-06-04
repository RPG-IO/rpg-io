package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import com.kkafara.rt.Result;
import io.rpg.model.actions.ConditionType;
import io.rpg.util.ErrorMessageBuilder;
import org.jetbrains.annotations.Nullable;

public class ConditionConfigBundle implements ConfigWithValidation {

  @Nullable
  private ConditionType type;

  /**
   * {@link io.rpg.model.actions.condition.ItemRequiredCondition} <br>
   * {@link io.rpg.model.actions.condition.DefeatOpponentCondition}
   */
  @Nullable
  @SerializedName(value = "item-tag", alternate = {"itemTag", "opponent-tag", "tag", "opponentTag"})
  private String objectTag;

  @Nullable
  @SerializedName(value = "level", alternate = {"required-level", "requiredLevel"})
  private Integer requiredLevel;

  @Nullable
  public ConditionType getType() {
    return type;
  }

  @Nullable
  public String getObjectTag() {
    return objectTag;
  }

  @Nullable
  public Integer getRequiredLevel() {
    return requiredLevel;
  }

  Result<Void, Exception> validateItemRequired() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (objectTag == null || objectTag.isBlank()) {
      builder.append("No or invalid item tag provided");
    }

    return builder.isEmpty() ? Result.ok() : Result.err(new Exception(builder.toString()));
  }

  Result<Void, Exception> validateDefeatOpponent() {
    // We need to check the same conditions
    return validateItemRequired();
  }

  Result<Void, Exception> validateLevelRequired() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (requiredLevel == null) {
      builder.append("No level provided");
    } else if (requiredLevel <= 0) {
      builder.append("Level must be > 0");
    }

    return builder.isEmpty() ? Result.ok() : Result.err(new Exception(builder.toString()));
  }

  @Override
  public Result<Void, Exception> validate() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (type == null) {
      builder.append("No or invalid type provided");
    }

    if (!builder.isEmpty()) {
      return Result.err(new Exception(builder.toString()));
    }

    switch (type) {
      case ITEM_REQUIRED -> { return validateItemRequired(); }
      case DEFEAT_OPPONENT -> { return validateDefeatOpponent(); }
      case LEVEL_REQUIRED -> { return validateLevelRequired(); }
      default -> throw new IllegalArgumentException("Not implemented condition type!");
    }
  }
}
