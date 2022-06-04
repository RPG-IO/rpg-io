package io.rpg.util;

import io.rpg.config.model.ActionConfigBundle;
import io.rpg.model.actions.*;
import io.rpg.model.actions.condition.ConditionFactory;
import io.rpg.model.object.Question;
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
      default -> {
        throw new IllegalArgumentException("Unexpected action type!");
      }
    }

  }

  private static QuizAction quizActionFromConfig(ActionConfigBundle config) {
    // TODO: as for now only one question is handled but the config API
    // is capable of handling more than one!
    return new QuizAction(config.getQuestions().get(0), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static GameEndAction gameEndActionFromConfig(ActionConfigBundle config) {
    return new GameEndAction(config.getDescription(), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static DialogueAction dialogueActionFromConfig(ActionConfigBundle config) {
    // TODO: Move Image creation inside DialogueAction? Idk tbh.
    return new DialogueAction(config.getDialogueStatements().get(0),
        new Image("file:" + config.getAssetPath()), ConditionFactory.fromConfig(config.getCondition()));
  }

  private static LocationChangeAction locationChangeActionFromConfig(ActionConfigBundle config) {
    return new LocationChangeAction(config.getTargetLocationTag(), config.getTargetPosition(),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static ShowDescriptionAction showDescriptionActionFromConfig(ActionConfigBundle config) {
    return new ShowDescriptionAction(config.getDescription(), new Image("file:" + config.getAssetPath()),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static BattleAction battleActionFromConfig(ActionConfigBundle config) {
    return new BattleAction(config.getRewardPoints(),
        ConditionFactory.fromConfig(config.getCondition()));
  }

  private static void initBeforeAndAfterActions(Action action, ActionConfigBundle config) {
    if (config.getBeforeAction() != null) {
      action.setBeforeAction(actionByType(config.getBeforeAction()));
    }
    if (config.getAfterAction() != null) {
      action.setAfterAction(actionByType(config.getAfterAction()));
    }
  }
}
