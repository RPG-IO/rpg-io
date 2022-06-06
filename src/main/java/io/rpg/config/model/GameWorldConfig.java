package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import com.kkafara.rt.Result;
import io.rpg.util.ErrorMessageBuilder;
import org.apache.logging.log4j.core.util.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This class in not meant to be instantiated by hand. It is used by {@link io.rpg.config.ConfigLoader}
 * via Gson.
 */
public class GameWorldConfig implements ConfigWithValidation {
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
   * Specifies the assets for each popup.
   */
  private String quizPopupBackground;
  private String textPopupButton;
  private String textImagePopupBackground;
  private String textImagePopupButton;
  private String textPopupBackground;
  private String inventoryPopupBackground;
  private String dialoguePopupBackground;
  private String npcFrame;

  /**
   * Describes path to directory with all the assets.
   *
   * Must be either absolute or relative to <b>configuration directory</b>.
   */
  @SerializedName(value = "assetDirPath", alternate = {"asset-dir", "asset-dir-path", "assetDir"})
  private String assetDirPath;


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
    locationTags.forEach(location -> builder.append("\tlocation-tag: ").append(location).append('\n'));
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
  public Result<Void, Exception> validateStageOne() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (locationTags != null && locationTags.size() < 1) {
			builder.append("No location tags detected");
    }
    if (tag == null) {
      builder.append("Null tag");
    }
    if (assetDirPath == null) {
      builder.append("No asset dir path specified");
    } else if (!Files.isDirectory(Path.of(assetDirPath))) {

    }
    if (playerConfig == null) {
      builder.append("No player config provided");
    }
    if (rootLocation == null) {
      builder.append("No root location set!");
    }
    if (quizPopupBackground == null || !Files.isRegularFile(Path.of(quizPopupBackground))) {
			builder.append("Invalid quiz popup background specified");
    } 
		if (textImagePopupBackground == null || !Files.isRegularFile(Path.of(textImagePopupBackground))) {
			builder.append("Invalid text image popup background specified");
    } 
		if (textPopupButton == null || !Files.isRegularFile(Path.of(textPopupButton))) {
			builder.append("Invalid text popup button specified");
    } 
		if (textImagePopupButton == null || !Files.isRegularFile(Path.of(textImagePopupButton))) {
			builder.append("Invald text image popup button specified");
    } 
		if (textPopupBackground == null || !Files.isRegularFile(Path.of(textPopupBackground))) {
			builder.append("Invalid text popup background specified");
    } 
		if (inventoryPopupBackground == null || !Files.isRegularFile(Path.of(inventoryPopupBackground))) {
			builder.append("Invalid inventory popup background specified");
    }
    if (dialoguePopupBackground == null || !Files.isRegularFile(Path.of(dialoguePopupBackground))) {
      builder.append("Invalid dialogue popup background specified");
    }
    if (npcFrame == null || !Files.isRegularFile(Path.of(npcFrame))) {
      builder.append("Invalid NPC Frame specified");
    }

    return builder.isEmpty() ? Result.ok() :
        Result.err(new IllegalStateException(builder.toString()));
  }

  /**
   * Allows for validation of the object's state.
   *
   * @return Object with valid state or exception.
   */
  public Result<Void, Exception> validate() {
    Result<Void, Exception> stageOneValidationResult = validateStageOne();
    ErrorMessageBuilder builder = new ErrorMessageBuilder();

    if (stageOneValidationResult.isErr()) {
      builder.combine(stageOneValidationResult.getErr().getMessage());
    }
    if (locationConfigs.size() < 1) {
      builder.append("No location configs loaded");
    }

    return builder.isEmpty() ? Result.ok() :
        Result.err(new IllegalStateException(builder.toString()));
  }

  public String getQuizPopupBackground() {
    return resolvePathFormat(quizPopupBackground);
  }

  public String getTextPopupButton() {
    return resolvePathFormat(textPopupButton);
  }

  public String getTextImagePopupBackground() {
    return resolvePathFormat(textImagePopupBackground);
  }

  public String getTextImagePopupButton() {
    return resolvePathFormat(textImagePopupButton);
  }

  public String getTextPopupBackground() {
    return resolvePathFormat(textPopupBackground);
  }

  public String getInventoryPopupBackground() {
    return resolvePathFormat(inventoryPopupBackground);
  }

  public String getDialoguePopupBackground() {
    return resolvePathFormat(dialoguePopupBackground);
  }

  public String getNpcFrame() {
    return resolvePathFormat(npcFrame);
  }

  public static String resolvePathFormat(String path) {
    return "file:" + path;
  }
}
