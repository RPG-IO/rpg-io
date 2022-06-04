package io.rpg.model.actions.condition;

import io.rpg.controller.Controller;

import java.lang.ref.WeakReference;

public final class ConditionEngine {
  private final WeakReference<Controller> weakRefController;

  public ConditionEngine(final Controller controller) {
    this.weakRefController = new WeakReference<>(controller);
  }

  public boolean evaluate(ItemRequiredCondition condition) {
    // TODO: Implement this when the inventory gets implemented.
    // Scheme:
//    return weakRefControler
//        .get()
//        .getPlayerController()
//        .getPlayer()
//        .getInventory()
//        .getItems()
//        .containsItemForTag(condition.getItemTag());
    return true;
  }

  public boolean evaluate(DefeatOpponentCondition condition) {
    return weakRefController
        .get()
        .getPlayerController()
        .getPlayer()
        .getDefeatedOpponents()
        .contains(condition.getOpponentTag());
  }

  public boolean evaluate(LevelRequiredCondition condition) {
    // TODO: Implement this when the level system is implemented.
    // For now I just return true.
//    return weakRefController
//        .get()
//        .getPlayerController()
//        .getPlayer()
//        .getLevel() >= condition.getLevel();
    return true;
  }
}
