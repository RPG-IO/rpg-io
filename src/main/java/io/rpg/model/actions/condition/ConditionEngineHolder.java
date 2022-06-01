package io.rpg.model.actions.condition;

import com.kkafara.rt.Result;
import io.rpg.controller.Controller;

public class ConditionEngineHolder {
  private static ConditionEngine instance;

  public static Result<Void, Void> init(final Controller controller) {
    if (instance == null) {
      instance = new ConditionEngine(controller);
      return Result.ok();
    }
    return Result.err();
  }

  public static ConditionEngine getInstance() {
    if (instance == null) {
      throw new IllegalStateException("Usage of ConditionEngineHolder before it was initialized");
    }
    return instance;
  }
}
