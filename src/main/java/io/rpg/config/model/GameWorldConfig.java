package io.rpg.config.model;

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

  private GameWorldConfig() {
    locationTags = new ArrayList<>();
    locationConfigs = new ArrayList<>();
  }

  /**
   * Unique tag for the game. This can be treated as name of the game.
   * @return String representing name of the game
   */
  public String getTag() {
    return tag;
  }

  /**
   * @return List containing names of locations
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

  public boolean validate() {
    // TODO
    return true;
  }
}
