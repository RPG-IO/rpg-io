package io.rpg.model.actions.condition;

import io.rpg.controller.Controller;
import io.rpg.model.object.Player;

import java.lang.ref.WeakReference;

public class ConditionEngine {
  private final WeakReference<Controller> controller;

  ConditionEngine(final Controller controller) {
    this.controller = new WeakReference<>(controller);
  }

  public boolean evaluate(Condition condition) {
    return true;
  }
}
