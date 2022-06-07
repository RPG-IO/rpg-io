package io.rpg.model.actions;

import io.rpg.controller.Controller;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import io.rpg.util.BattleResult;
import io.rpg.util.PathUtils;
import io.rpg.view.GameObjectView;
import io.rpg.view.InventoryGameObjectView;
import io.rpg.view.LocationView;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public final class ActionEngine {

  private final WeakReference<Controller> weakRefController;

  private final Logger logger = LogManager.getLogger(ActionEngine.class);

  public ActionEngine(final Controller controller) {
    weakRefController = new WeakReference<>(controller);
  }

  @NotNull
  private Controller controller() throws IllegalStateException {
    Controller controller = weakRefController.get();
    if (controller == null) throw new IllegalStateException("Controller was dropped but ActionEngine still executes");
    return controller;
  }

  public void onAction(LocationChangeAction action) {
    actionGuard(action, () -> {
      if (!controller().getTagToLocationModelMap().containsKey(action.destinationLocationTag)) {
        logger.error("Unknown location tag");
        return;
      }

      LocationView nextView = controller().getTagToLocationViewMap().get(action.destinationLocationTag);
      LocationModel nextModel = controller().getTagToLocationModelMap().get(action.destinationLocationTag);

      controller().getPlayerController().teleportTo(nextModel, nextView, action.playerPosition);

      controller().setCurrentModel(nextModel);
      controller().setView(nextView);
      controller().getMainStage().setScene(nextView);
    });
  }

  public void onAction(DialogueAction action) {
    actionGuard(action, () -> {
      var controller = controller();
      controller.getPopupController().openDialoguePopup(action.text, action.image,
          controller.getWindowCenterX(), controller.getWindowCenterY());
    });
  }

  public void onAction(ShowDescriptionAction action) {
    actionGuard(action, () -> {
      if (!action.description.isEmpty()) {
        var controller = controller();
        controller.getPopupController().openTextImagePopup(action.description, action.image,
            controller.getWindowCenterX(), controller.getWindowCenterY());
      }
    });
  }

  public void onAction(QuizAction action) {
    actionGuard(action, () -> {
      var controller = controller();
      int pointsCount = action.getPointsToEarn();
      System.out.println(pointsCount);
      controller.getPopupController().openQuestionPopup(
          action.question,
          controller.getWindowCenterX(), controller.getWindowCenterY(),
          (won, points) -> acceptQuizResult(action.getEmitter(), won, points),
          pointsCount
      );
    });
  }

  public void acceptQuizResult(GameObject opponent, boolean correct, int pointsCount) {
    var controller = controller();
    System.out.println("quiz result accepting");

    if (correct) {
      if (pointsCount > 0) {
        controller.getPopupController().openPointsPopup(pointsCount, controller.getWindowCenterX(), controller.getWindowCenterY());
        controller.getPlayerController().getPlayer().addDefeatedOpponent(opponent.getTag());
        controller.getPlayerController().addPoints(pointsCount);
      } else {
        controller.getPopupController().hidePopup();
      }
    } else {
      controller.getPopupController().hidePopup();
      logger.info("Wrong answer provided");
    }

    // TODO: better action disabling
    opponent.setOnLeftClickAction(Action.VOID);
  }

  public void onAction(GameEndAction action) {
    actionGuard(action, () -> {
      var controller = controller();
      controller.getGameEndController().showGameEnd(controller.getMainStage(), action.description);
    });
  }

  public void onAction(BattleAction action) {
    actionGuard(action, () -> {
      Player player = controller().getPlayerController().getPlayer();
      GameObject opponent = action.getOpponent();
      int reward = action.getReward();
      BattleResult result;
      if (player.getStrength() > opponent.getStrength()) {
        player.addDefeatedOpponent(opponent.getTag());
        controller().removeObjectFromModel(opponent);
        result = new BattleResult(BattleResult.Result.VICTORY, reward);
      } else if (player.getStrength() < opponent.getStrength()) {
        result = new BattleResult(BattleResult.Result.DEFEAT, 0);
      } else {
        result = new BattleResult(BattleResult.Result.DRAW, 0);
      }
      controller().getPopupController().openTextPopup(result.getMessage(),
          controller().getWindowCenterX(), controller().getWindowCenterY());
      if (player.getStrength() > opponent.getStrength())
        player.addPoints(reward);
    });
  }

  public void onAction(LevelUpAction action) {
    controller().getPopupController().openTextPopup((action.getPoints() != null ? (action.getPoints() + " points earned already!\n") : "" ) + "Achieved level " + action.newLevel + "!",
            controller().getWindowCenterX(), controller().getWindowCenterY());
  }

  public void onAction(BattleReflexAction action) {
    actionGuard(action, () -> controller().getPopupController().openBattleReflexPopup(action.getReward(),
        (won, points) -> acceptBattleReflexResult(action.getEmitter(), won, points),
        controller().getWindowCenterX(),
        controller().getWindowCenterY()));
  }

  public void acceptBattleReflexResult(GameObject actionOwner, boolean won, int pointsCount) {
    var controller = controller();
    if (won) {
      if (pointsCount > 0)
        controller.getPopupController().openPointsPopup(pointsCount, controller.getWindowCenterX(), controller.getWindowCenterY());
      controller.removeObjectFromModel(actionOwner);
      controller.getPlayerController().getPlayer().addDefeatedOpponent(actionOwner.getTag());
      controller.getPlayerController().addPoints(pointsCount);
    } else {
      controller.getPopupController().hidePopup();
    }
  }

  public void onAction(CollectAction action) {
    actionGuard(action, () -> {
      var controller = controller();
      controller.getPopupController().openTextImagePopup("Picked up an item!", new Image(PathUtils.resolvePathToJFXFormat(action.getAssetPath())),
          controller.getWindowCenterX(), controller.getWindowCenterY());
      controller.getPlayerController().getPlayer().getInventory()
          .add(new InventoryGameObjectView(action.getAssetPath(), action.getDescription(), action.getOwner().getTag()));
      controller.removeObjectFromModel(action.getOwner());
    });
  }

  private void actionGuard(BaseAction action, Runnable actionLogic) {
    if (action.getCondition() != null && !action.getCondition().acceptEngine(controller().getConditionEngine())) {
      logger.info("Action not executed due to condition being not satisfied");
      return;
    }
    actionLogic.run();
  }
}
