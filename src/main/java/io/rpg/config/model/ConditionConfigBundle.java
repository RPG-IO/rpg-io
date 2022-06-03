package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import com.kkafara.rt.Result;
import io.rpg.model.actions.ConditionType;
import io.rpg.util.ErrorMessageBuilder;
import org.jetbrains.annotations.Nullable;

public class ConditionConfigBundle implements ConfigWithValidation {

  @Nullable
  private ConditionType type;

  @Nullable
  @SerializedName(value = "item-tag", alternate = {"itemTag", "opponent-tag", "tag"})
  private String objectTag;

  @Nullable
  public ConditionType getType() {
    return type;
  }

  @Nullable
  public String getObjectTag() {
    return objectTag;
  }

  Result<Void, Exception> validateItemRequired() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (objectTag == null || objectTag.isBlank()) {
      builder.append("No or invalid item tag provided");
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
      default -> throw new IllegalArgumentException("Not implemented condition type!");
    }
  }
}
