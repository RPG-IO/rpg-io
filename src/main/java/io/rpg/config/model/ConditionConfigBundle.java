package io.rpg.config.model;

import com.kkafara.rt.Result;
import io.rpg.model.actions.ConditionType;
import org.jetbrains.annotations.Nullable;

public class ConditionConfigBundle implements ConfigWithValidation {

  @Nullable
  private ConditionType type;

  @Nullable
  public ConditionType getType() {
    return type;
  }

  @Override
  public Result<Void, Exception> validate() {
    return Result.ok();
  }
}
