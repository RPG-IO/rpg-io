package io.rpg.config.model;

import com.kkafara.rt.Result;

public interface ConfigWithValidation {
  Result<Void, Exception> validate();
}
