package io.rpg.util;

import io.rpg.config.model.GameObjectConfig;
import io.rpg.config.model.PlayerConfig;
import io.rpg.model.actions.Action;
import io.rpg.model.actions.BattleAction;
import io.rpg.model.actions.ShowDescriptionAction;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import javafx.scene.image.Image;


import java.util.LinkedList;

/**
 * Exposes collection of methods to create {@link io.rpg.model.object.GameObject} class instances.
 */
public class GameObjectFactory {
  /**
   * Creates {@link GameObject} instance basing on information contained in config.
   *
   * @param config description of object properties
   * @return game object created based on information located in config
   */
  public static GameObject fromConfig(GameObjectConfig config) {

    Action onLeftClickAction = config.getOnLeftClick() != null ? ActionFactory.fromConfig(config.getOnLeftClick()) : Action.VOID;
    Action onRightClickAction = config.getOnRightClick() != null ? ActionFactory.fromConfig(config.getOnRightClick()) : Action.VOID;
    Action onApproach = config.getOnApproach() != null ? ActionFactory.fromConfig(config.getOnApproach()) : Action.VOID;

    if (onRightClickAction.equals(Action.VOID)) {
      onRightClickAction = new ShowDescriptionAction(
          DataObjectDescriptionProvider.combineDescriptions(
              DataObjectDescriptionProvider.getFieldDescription(config, GameObjectConfig.class)
          ),
          new Image("file:" + config.getAssetPath()),
          null
      );
    }

    // Not implemented in model for now, however they should be
    Action onClickAction = config.getOnClick() != null ? ActionFactory.fromConfig(config.getOnClick()) : Action.VOID;



    if (config instanceof PlayerConfig) {
      Player player = new Player(config.getTag(), config.getPosition(), config.getAssetPath());
      player.setOnLeftClickAction(onLeftClickAction);
      player.setOnRightClickAction(onRightClickAction);
      player.setOnApproach(onApproach);
//      player.setStrength(config.getStrength());
      return player;
    } else {
      GameObject gameObject = new GameObject(config.getTag(), config.getPosition());

      gameObject.setOnLeftClickAction(onLeftClickAction);
      gameObject.setOnRightClickAction(onRightClickAction);
      gameObject.setOnApproach(onApproach);

      if (onLeftClickAction instanceof BattleAction) {
        ((BattleAction) onLeftClickAction).setOpponent(gameObject);
      }
      if (onRightClickAction instanceof BattleAction) {
        ((BattleAction) onRightClickAction).setOpponent(gameObject);
      }

      // TODO: Create ActionFactory & inflate the actions
      return gameObject;
    }
  }

  /**
   * Creates list of {@link GameObject} instances based on information contained in list of configs.
   * This method guarantees that {@link GameObject}s located in result list are in the same order,
   * i.e. first {@link GameObject} is created from first {@link GameObjectConfig}, second from second
   * etc.
   *
   * @param configs descriptions of object properties
   * @return game objects created based on information located in config list
   */
  public static LinkedList<GameObject> fromConfigs(Iterable<GameObjectConfig> configs) {
    LinkedList<GameObject> gameObjects = new LinkedList<>();
    for (GameObjectConfig config : configs) {
      gameObjects.add(fromConfig(config));
    }
    return gameObjects;
  }
}
