package io.rpg.model.actions.condition;

import io.rpg.controller.Controller;

import java.lang.ref.WeakReference;

public final class ConditionEngine {
  private final WeakReference<Controller> weakRefController;

  public ConditionEngine(final Controller controller) {
    this.weakRefController = new WeakReference<>(controller);
  }

  private Controller controller() throws IllegalStateException {
    Controller controller = weakRefController.get();
    if (controller == null) throw new IllegalStateException("Controller was dropped but ConditionEngine still executes");
    return controller;
  }

  public boolean evaluate(ItemRequiredCondition condition) {
    return controller()
        .getPlayerController()
        .getPlayer()
        .getInventory()
        .containsItemForTag(condition.getItemTag());
  }

  public boolean evaluate(DefeatOpponentCondition condition) {
    return controller()
        .getPlayerController()
        .getPlayer()
        .getDefeatedOpponents()
        .contains(condition.getOpponentTag());
  }

  public boolean evaluate(LevelRequiredCondition condition) {
    return controller()
        .getPlayerController()
        .getPlayer()
        .getLevel() >= condition.getLevel();
  }

  public boolean evaluate(PointsRequiredCondition condition) {
    return controller()
        .getPlayerController()
        .getPlayer()
        .getPoints() >= condition.getPoints();
  }
}
