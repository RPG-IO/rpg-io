package io.rpg.util;

import io.rpg.config.model.GameObjectConfig;
import io.rpg.model.data.Vector;
import io.rpg.model.object.*;
import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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
    // TODO: add case for Player type here!
    switch (GameObject.Type.valueOf(config.getTypeString().toUpperCase())) {
      case COLLECTIBLE -> { return new CollectibleGameObject(config.getTag(), config.getPosition()); }
      case DIALOG -> { return new DialogGameObject(config.getTag(), config.getPosition()); }
      case PLAYER -> { return new Player(config.getTag(), config.getPosition()); }
      case NAVIGABLE -> { return new NavigationalGameObject(config.getTag(), config.getPosition()); }
      default -> throw new RuntimeException("Unknown GameObject type. This should not happen!");
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
  public static LinkedList<GameObject> fromConfigList(List<GameObjectConfig> configs) {
    LinkedList<GameObject> gameObjects = new LinkedList<>();
    for (GameObjectConfig config : configs) {
      gameObjects.add(fromConfig(config));
    }
    return gameObjects;
  }
}
