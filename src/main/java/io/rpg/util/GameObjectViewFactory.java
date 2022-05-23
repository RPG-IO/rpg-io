package io.rpg.util;

import io.rpg.config.model.GameObjectConfig;
import io.rpg.view.GameObjectView;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * Exposes collection of methods to create {@link GameObjectView} class instances.
 */
public class GameObjectViewFactory {
  /**
   * Creates {@link GameObjectView} instance basing on information contained in config.
   *
   * @param config description of object properties
   * @return game object view created based on information located in config
   */
  public static GameObjectView fromConfig(GameObjectConfig config) {
    // TODO: consider returning Result type & validate the construction
    return new GameObjectView(Path.of(config.getAssetPath()), config.getPosition());
  }

  /**
   * Creates list of {@link GameObjectView}s instances based on information contained in list of configs.
   * This method guarantees that {@link GameObjectView}s located in result list are in the same order,
   * i.e. first {@link GameObjectView} is created from first {@link GameObjectConfig}, second from second
   * etc.
   *
   * @param configs descriptions of object properties
   * @return game object views created based on information located in config list
   */
  public static LinkedList<GameObjectView> fromConfigs(Iterable<GameObjectConfig> configs) {
    LinkedList<GameObjectView> gameObjects = new LinkedList<>();
    for (GameObjectConfig config : configs) {
      gameObjects.add(fromConfig(config));
    }
    return gameObjects;
  }
}
