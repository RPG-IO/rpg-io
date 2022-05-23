package io.rpg.util;

import io.rpg.config.model.ActionConfigBundle;
import io.rpg.model.actions.*;
import io.rpg.model.object.Question;
import javafx.scene.image.Image;

public class ActionFactory {
  /**
   *
   * @param config
   * @return
   */
  public static Action fromConfig(ActionConfigBundle config) {
    assert config.getActionType() != null : "Null action type! Make sure to call validation " +
        "method after the config object is inflated from JSON!";

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
      default -> {
        throw new IllegalArgumentException("Unexpected action type!");
      }
    }
  }

  private static QuizAction quizActionFromConfig(ActionConfigBundle config) {
    // TODO: as for now only one question is handled but the config API
    // is capable of handling more than one!
    return new QuizAction(config.getQuestions().get(0));
  }

  private static GameEndAction gameEndActionFromConfig(ActionConfigBundle config) {
    return new GameEndAction(config.getDescription());
  }

  private static DialogueAction dialogueActionFromConfig(ActionConfigBundle config) {
    // TODO: Move Image creation inside DialogueAction? Idk tbh.
    return new DialogueAction(config.getDescription(), new Image(config.getAssetPath()));
  }

  private static LocationChangeAction locationChangeActionFromConfig(ActionConfigBundle config) {
    return new LocationChangeAction(config.getTargetLocationTag(), config.getTargetPosition());
  }

  private static ShowDescriptionAction showDescriptionActionFromConfig(ActionConfigBundle config) {
    return new ShowDescriptionAction(config.getDescription(), new Image(config.getAssetPath()));
  }
}
