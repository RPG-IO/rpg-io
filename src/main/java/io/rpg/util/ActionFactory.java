package io.rpg.util;

import io.rpg.config.model.ActionConfigBundle;
import io.rpg.model.actions.*;
import io.rpg.model.actions.condition.ConditionFactory;
import javafx.scene.image.Image;

public class ActionFactory {
  /**
   *
   * @param config
   * @return
   */
  public static Action fromConfig(ActionConfigBundle config) {
    if (config == null) {
      return Action.VOID;
    }

    assert config.getActionType() != null : "Null action type! Make sure to call validation " +
        "method after the config object is inflated from JSON!";

    Action result = actionByType(config);
    initBeforeAndAfterActions(result, config);

    return result;
  }

  private static Action actionByType(ActionConfigBundle config) {
    switch (config.getActionType()) {
      case Quiz -> {
        return quizActionFromConfig(config);
      }
      case GameEnd -> {
        return gameEndActionFromConfig(config);
      }
      case Dialogue -> {
        return dialogueActionFromConfig(config);
      }
      case LocationChange -> {
        return locationChangeActionFromConfig(config);
      }
      case ShowDescription -> {
        return showDescriptionActionFromConfig(config);
      }
      case Battle -> {
        return battleActionFromConfig(config);
      }
      case BattleReflex -> {
        return battleReflexActionFromConfig(config);
      }
      case Collect -> {
        return collectActionFromConfig(config);
      }
      default -> throw new IllegalArgumentException("Unexpected action type!");
    }
  }

  private static QuizAction quizActionFromConfig(ActionConfigBundle config) {
    // TODO: as for now only one question is handled but the config API
    // is capable of handling more than one!
    return new QuizAction(config.getQuestions().get(0), config.getRewardPoints(), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static GameEndAction gameEndActionFromConfig(ActionConfigBundle config) {
    return new GameEndAction(config.getDescription(), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static DialogueAction dialogueActionFromConfig(ActionConfigBundle config) {
    // TODO: Move Image creation inside DialogueAction? Idk tbh.
    return new DialogueAction(config.getDialogueStatements().get(0),
        new Image(PathUtils.resolvePathToJFXFormat(config.getAssetPath())), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static LocationChangeAction locationChangeActionFromConfig(ActionConfigBundle config) {
    return new LocationChangeAction(config.getTargetLocationTag(), config.getTargetPosition(),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static ShowDescriptionAction showDescriptionActionFromConfig(ActionConfigBundle config) {
    return new ShowDescriptionAction(config.getDescription(), new Image(
        PathUtils.resolvePathToJFXFormat(PathUtils.resolvePathToAsset(config.getAssetPath()).get())),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static BattleAction battleActionFromConfig(ActionConfigBundle config) {
    return new BattleAction(config.getRewardPoints(), config.getRequiredStrength(),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static BattleReflexAction battleReflexActionFromConfig(ActionConfigBundle config) {
    return new BattleReflexAction(config.getRewardPoints(), config.getRequiredStrength(),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static void initBeforeAndAfterActions(Action action, ActionConfigBundle config) {
    if (config.getBeforeAction() != null) {
      Action newAction = actionByType(config.getBeforeAction());
      initBeforeAndAfterActions(newAction, config.getBeforeAction());
      action.setBeforeAction(newAction);
    }
    if (config.getAfterAction() != null) {
      Action newAction = actionByType(config.getAfterAction());
      initBeforeAndAfterActions(newAction, config.getAfterAction());
      action.setAfterAction(newAction);
    }
  }

  private static CollectAction collectActionFromConfig(ActionConfigBundle config) {
    return new CollectAction(PathUtils.resolvePathToJFXFormat(
        PathUtils.resolvePathToAsset(config.getAssetPath()).get()),
        config.getDescription(),
        ConditionFactory.fromConfig(config.getCondition()));
  }
}
