package io.rpg.config.model;

import static io.rpg.util.PathUtils.resolvePathToAsset;
import static io.rpg.util.PathUtils.resolvePathToJFXFormat;

import com.google.gson.annotations.SerializedName;
import com.kkafara.rt.Result;
import io.rpg.util.ErrorMessageBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


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
  private String assetDir;
  private transient Path assetDirPath;

  /**
   * Ugly as ... But we need it to be able to resolve the paths.
   * Better solution might be moving path validation to new class or just to ConfigLoader.
   */
  private transient Path configDirPath;

  public void injectConfigDirPath(Path path) {
    configDirPath = path;
  }


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
    if (assetDir == null || assetDir.isBlank()) {
      builder.append("No asset dir path specified");
    } else if (!Files.isDirectory(Path.of(assetDir))) {
      // the path is either invalid or relative to configuration directory
      // let's see whether it is relative first
      assetDirPath = configDirPath.resolve(assetDir);
      if (!Files.isDirectory(assetDirPath)) {
        builder.append("Invalid asset directory specified. Neither " + assetDir + " nor " + assetDirPath + " "
            + "point to valid directory");
      } else {
        assetDir = assetDirPath.toString();
      }
    } else {
      assetDirPath = Path.of(assetDir);
    }

    if (playerConfig == null) {
      builder.append("No player config provided");
    }

    if (rootLocation == null) {
      builder.append("No root location set!");
    }

    resolvePathToAsset(assetDirPath, quizPopupBackground).ifPresentOrElse(
        pathStr -> { quizPopupBackground = pathStr; },
        () -> builder.append("Invalid quiz popup background specified")
    );

    resolvePathToAsset(assetDirPath, textImagePopupBackground).ifPresentOrElse(
        pathStr -> { textImagePopupBackground = pathStr; },
        () -> builder.append("Invalid text image popup background specified")
    );

    resolvePathToAsset(assetDirPath, textPopupButton).ifPresentOrElse(
        pathStr -> { textPopupButton = pathStr; },
        () -> builder.append("Invalid text popup button specified")
    );

    resolvePathToAsset(assetDirPath, textImagePopupButton).ifPresentOrElse(
        pathStr -> { textImagePopupButton = pathStr; },
        () -> builder.append("Invalid text image popup button specified")
    );

    resolvePathToAsset(assetDirPath, textPopupBackground).ifPresentOrElse(
        pathStr -> { textPopupBackground = pathStr; },
        () -> builder.append("Invalid text popup background specified")
    );

    resolvePathToAsset(assetDirPath, inventoryPopupBackground).ifPresentOrElse(
        pathStr -> { inventoryPopupBackground = pathStr; },
        () -> builder.append("Invalid inventory popup background specified")
    );

    resolvePathToAsset(assetDirPath, dialoguePopupBackground).ifPresentOrElse(
        pathStr -> { dialoguePopupBackground = pathStr; },
        () -> builder.append("Invalid dialogue popup background specified")
    );

    resolvePathToAsset(assetDirPath, npcFrame).ifPresentOrElse(
        pathStr -> { npcFrame = pathStr; },
        () -> builder.append("Invalid NPC Frame specified")
    );

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
    return resolvePathToJFXFormat(quizPopupBackground);
  }

  public String getTextPopupButton() {
    return resolvePathToJFXFormat(textPopupButton);
  }

  public String getTextImagePopupBackground() {
    return resolvePathToJFXFormat(textImagePopupBackground);
  }

  public String getTextImagePopupButton() {
    return resolvePathToJFXFormat(textImagePopupButton);
  }

  public String getTextPopupBackground() {
    return resolvePathToJFXFormat(textPopupBackground);
  }

  public String getInventoryPopupBackground() {
    return resolvePathToJFXFormat(inventoryPopupBackground);
  }

  public String getDialoguePopupBackground() {
    return resolvePathToJFXFormat(dialoguePopupBackground);
  }

  public String getNpcFrame() {
    return resolvePathToJFXFormat(npcFrame);
  }
}
