package io.rpg;

import io.rpg.config.ConfigLoader;
import io.rpg.controller.Controller;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.config.model.GameObjectConfig;
import io.rpg.util.Result;
import io.rpg.view.LocationView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Initializer {
  private Path pathToConfigDir;
  private ConfigLoader configLoader;
  private Stage mainStage;

  private final Logger logger;

  public Initializer(@NotNull String pathToConfigDir, Stage mainStage) {
    this.configLoader = new ConfigLoader(pathToConfigDir);
    this.mainStage = mainStage;
    this.logger = LogManager.getLogger(Initializer.class);
  }

  public Result<Game, Exception> initialize() {
    Result<GameWorldConfig, Exception> gameWorldConfigLoadResult = configLoader.load();

    if (gameWorldConfigLoadResult.isError()) {
      gameWorldConfigLoadResult.getErrorValueOpt().ifPresentOrElse(
          ex -> logger.error(ex.getMessage()),
          () -> logger.error("Unknown error returned from config loader")
      );
      return Result.error(gameWorldConfigLoadResult.getErrorValue());
    } else if (gameWorldConfigLoadResult.getOkValue() == null) {
      logger.error("ConfigLoader fetched null GameWorldConfig");
      return Result.error(new RuntimeException("ConfigLoader fetched null GameWorldConfig"));
    }

    GameWorldConfig worldConfig = gameWorldConfigLoadResult.getOkValue();

    Controller.Builder controllerBuilder = new Controller.Builder();

    assert worldConfig.getLocationConfigs() != null;
    assert worldConfig.getLocationConfigs().size() > 0 : "There must be at least one location config specified";

    for (LocationConfig locationConfig : worldConfig.getLocationConfigs()) {
      LocationModel model = loadLocationModelFromConfig(locationConfig);
      LocationView view = loadLocationViewFromConfig(locationConfig);

      assert view != null;

      if (locationConfig.getTag().equals(worldConfig.getRootLocation())) {
        controllerBuilder
            .setModel(model)
            .setView(view);
      }

      model.addListener(view);

      controllerBuilder
          .addViewForTag(locationConfig.getTag(), view)
          .addModelForTag(locationConfig.getTag(), model);
    }

    Game.Builder gameBuilder = new Game.Builder();
    gameBuilder.setController(controllerBuilder.build());

    return Result.ok(gameBuilder.build());
  }

  @Nullable
  public static LocationView loadLocationViewFromConfig(LocationConfig config) {
    try {
      return LocationView.fromConfig(config);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static LocationModel loadLocationModelFromConfig(LocationConfig config) {
    List<GameObjectConfig> gameObjectConfigs = config.getObjects();
    List<GameObject> gameObjects = new LinkedList<>();

    for (GameObjectConfig goconfig : gameObjectConfigs) {
      gameObjects.add(GameObject.fromConfig(goconfig));
    }

    return new LocationModel(
        config.getTag(),
        gameObjects
    );
  }
}
