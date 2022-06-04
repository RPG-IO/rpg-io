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
    // TODO: Implement this when the inventory gets implemented.
    // Scheme:
//    return controller()
//        .getPlayerController()
//        .getPlayer()
//        .getInventory()
//        .getItems()
//        .containsItemForTag(condition.getItemTag());
    return true;
  }

  public boolean evaluate(DefeatOpponentCondition condition) {
    return controller()
        .getPlayerController()
        .getPlayer()
        .getDefeatedOpponents()
        .contains(condition.getOpponentTag());
  }

  public boolean evaluate(LevelRequiredCondition condition) {
    // TODO: Implement this when the level system is implemented.
    // For now I just return true.
//    return controller()
//        .getPlayerController()
//        .getPlayer()
//        .getLevel() >= condition.getLevel();
    return true;
  }
}
