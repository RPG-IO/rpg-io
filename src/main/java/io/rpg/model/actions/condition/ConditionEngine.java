package io.rpg.model.actions.condition;

import io.rpg.controller.Controller;

import java.lang.ref.WeakReference;

public class ConditionEngine {
  private final WeakReference<Controller> weakRefControler;

  public ConditionEngine(final Controller controller) {
    this.weakRefControler = new WeakReference<>(controller);
  }

  public boolean evaluateItemRequiredCondition(ItemRequiredCondition itemRequiredCondition) {
    // TODO: Implement this when the inventory gets implemented.
    // Scheme:
//    return weakRefControler
//        .get()
//        .getPlayerController()
//        .getPlayer()
//        .getInventory()
//        .getItems()
//        .containsItemForTag(itemRequiredCondition.getItemTag());
    return true;
  }
}
