package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import io.rpg.util.ErrorMessageBuilder;
import io.rpg.util.Result;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * This class in not meant to be instantiated by hand. It is used by {@link io.rpg.config.ConfigLoader}
 * via Gson.
 */
public class GameWorldConfig {
  /**
   * Unique tag for the game. This can be treated as name of the game.
   */
  @SerializedName(value = "tag", alternate = {"id"})
  private String tag;

  /**
   * List that contains all the names of locations specified by user.
   * This filed is inflated from information in root.json file.
   */
  @SerializedName(value = "locations", alternate = {"locationTags", "location-tags"})
  private Set<String> locationTags;

  /**
   * Contains inflated {@link io.rpg.config.model.LocationConfig}s of all the
   * locations specified by user.
   */
  private ArrayList<LocationConfig> locationConfigs;

  /**
   * Configuration for the player object.
   */
  @SerializedName(value = "player", alternate = {"playerConfig", "player-config"})
  private PlayerConfig playerConfig;

  /**
   * Private ctor as this class is not meant to be instantiated manually in any scenario.
   */
  private GameWorldConfig() {
    locationTags = new LinkedHashSet<>();
    locationConfigs = new ArrayList<>();
  }

  /**
   * Describes tag of the root location. (The location that is displayed first)
   */
  @SerializedName(value = "rootLocation", alternate = {"root-location"})
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
  public Set<String> getLocationTags() {
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
  public PlayerConfig getPlayerConfig() {
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
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (locationTags.size() < 1) {
      builder.append("No location tags detected");
    }
    if (tag == null) {
      builder.append("Null tag");
    }
    if (playerConfig == null) {
      builder.append("No player config provided");
    }
    if (rootLocation == null) {
      builder.append("No root location set!");
    }

    return builder.isEmpty() ? Result.ok(this) :
        Result.err(new IllegalStateException(builder.toString()));
  }

  /**
   * Allows for validation of the object's state.
   *
   * @return Object with valid state or exception.
   */
  public Result<GameWorldConfig, Exception> validate() {
    Result<GameWorldConfig, Exception> stageOneValidationResult = validateStageOne();
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (stageOneValidationResult.isErr()) {
      builder.combine(stageOneValidationResult.getErrValue().getMessage());
    }
    if (locationConfigs.size() < 1) {
      builder.append("No location configs loaded");
    }

    return builder.isEmpty() ? Result.ok(this) :
        Result.err(new IllegalStateException(builder.toString()));
  }
}
