package io.rpg.model.actions.condition;

import io.rpg.controller.Controller;

import java.lang.ref.WeakReference;

public class ConditionEngine {
  private final WeakReference<Controller> weakRefController;

  public ConditionEngine(final Controller controller) {
    this.weakRefController = new WeakReference<>(controller);
  }

  public boolean evaluateItemRequiredCondition(ItemRequiredCondition condition) {
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

  public boolean evaluateDefeatOpponentCondition(DefeatOpponentCondition condition) {
    return weakRefController
        .get()
        .getPlayerController()
        .getPlayer()
        .getDefeatedOpponents()
        .contains(condition.getOpponentTag());
  }
}
