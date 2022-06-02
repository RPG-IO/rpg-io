package io.rpg.config.model;

import com.kkafara.rt.Result;

public class ConditionConfig implements ConfigWithValidation {
  @Override
  public Result<Void, Exception> validate() {
    return Result.ok();
  }
}
