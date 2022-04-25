package io.rpg.config.model;

import io.rpg.util.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * This class in not meant to be instantiated by hand. It is used by {@link io.rpg.config.ConfigLoader}
 * via Gson.
 */
public class GameWorldConfig {
  /**
   * Unique tag for the game. This can be treated as name of the game.
   */
  private String tag;

  /**
   * List that contains all the names of locations specified by user.
   * This filed is inflated from information in root.json file.
   */
  private ArrayList<String> locationTags;

  /**
   * Contains inflated {@link io.rpg.config.model.LocationConfig}s of all the
   * locations specified by user.
   */
  private ArrayList<LocationConfig> locationConfigs;

  /**
   * Configuration for the player object.
   */
  private GameObjectConfig playerConfig;

  private GameWorldConfig() {
    locationTags = new ArrayList<>();
    locationConfigs = new ArrayList<>();
  }

  /**
   * Describes tag of the root location. (The location that is displayed first)
   */
  private String rootLocation;

  /**
   * Unique tag for the game. This can be treated as name of the game.
   *
   * @return String representing name of the game.
   */
  public String getTag() {
    return tag;
  }

  /**
   * @return List containing names of locations.
   */
  public List<String> getLocationTags() {
    return locationTags;
  }

  /**
   * @return inflated {@link io.rpg.config.model.LocationConfig}s of all the
   * locations specified by user.
   */
  public ArrayList<LocationConfig> getLocationConfigs() {
    return locationConfigs;
  }

  /**
   * @return Tag of the root location.
   */
  public String getRootLocation() {
    return rootLocation;
  }

  /**
   * @return configuration for the player
   */
  public GameObjectConfig getPlayerConfig() {
    return playerConfig;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\n{\n").append("\ttag: ").append(tag).append('\n');
    locationTags.forEach(location -> {
      builder.append("\tlocation-tag: ").append(location).append('\n');
    });
    return builder.append("}\n").toString();
  }

  public void addLocationConfig(LocationConfig locationConfig) {
    locationConfigs.add(locationConfig);
  }

  /**
   * As {@link GameWorldConfig} is loaded in two stages right now:
   * 1. first data from root.json is loaded first
   * 2. then location configs are added
   * validation after step 1 is also required.
   *
   * @return Current configuration state or exception.
   */
  public Result<GameWorldConfig, Exception> validateStageOne() {
    if (locationTags.size() < 1) {
      return Result.error(new IllegalStateException("No location tags detected"));
    } else if (tag == null) {
      return Result.error(new IllegalStateException("Null tag"));
    } else if (playerConfig == null) {
      return Result.error(new IllegalStateException("No player config provided"));
    } else {
      return Result.ok(this);
    }
  }

  /**
   * Allows for validation of the object's state.
   *
   * @return Object with valid state or exception.
   */
  public Result<GameWorldConfig, IllegalStateException> validate() {
    if (locationTags.size() < 1) {
      return Result.error(new IllegalStateException("No location tags detected"));
    } else if (locationConfigs.size() < 1) {
      return Result.error(new IllegalStateException("No location configs loaded"));
    } else if (tag == null) {
      return Result.error(new IllegalStateException("Null tag"));
    } else if (rootLocation == null) {
      return Result.error(new IllegalStateException("No root location set!"));
    } else {
      return Result.ok(this);
    }
  }
}
