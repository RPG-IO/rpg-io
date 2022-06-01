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
  @SerializedName(value = "item-tag", alternate = {"itemTag"})
  private String itemTag;

  @Nullable
  public ConditionType getType() {
    return type;
  }

  @Nullable
  public String getItemTag() {
    return itemTag;
  }

  @Override
  public Result<Void, Exception> validate() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (type == null) {
      builder.append("No or invalid type provided");
    }

    if (itemTag == null || itemTag.isBlank()) {
      builder.append("No or invalid item tag provided");
    }

    return builder.isEmpty() ? Result.ok() : Result.err(new Exception(builder.toString()));
  }
}
