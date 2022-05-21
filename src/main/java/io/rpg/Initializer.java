package io.rpg;

import io.rpg.config.ConfigLoader;
import io.rpg.controller.Controller;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import io.rpg.controller.PlayerController;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import io.rpg.util.GameObjectFactory;
import io.rpg.util.GameObjectViewFactory;
import io.rpg.util.Result;
import io.rpg.view.GameObjectView;
import io.rpg.view.LocationView;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class Initializer {
  private Path pathToConfigDir;
  private ConfigLoader configLoader;
  private final Stage mainStage;

  private final Logger logger;

  public Initializer(@NotNull String pathToConfigDir, @NotNull Stage mainStage) {
    this.configLoader = new ConfigLoader(pathToConfigDir);
    this.mainStage = mainStage;
    this.logger = LogManager.getLogger(Initializer.class);
  }

  public Result<Game, Exception> initialize() {
    Result<GameWorldConfig, Exception> gameWorldConfigLoadResult = configLoader.load();

    if (gameWorldConfigLoadResult.isErr()) {
      gameWorldConfigLoadResult.getErrValueOpt().ifPresentOrElse(
          ex -> logger.error(ex.getMessage()),
          () -> logger.error("Unknown error returned from config loader")
      );
      return Result.err(gameWorldConfigLoadResult.getErrValue());
    } else if (gameWorldConfigLoadResult.isOkValueNull()) {
      logger.error("ConfigLoader fetched null GameWorldConfig");
      return Result.err(new RuntimeException("ConfigLoader fetched null GameWorldConfig"));
    }

    GameWorldConfig gameWorldConfig = gameWorldConfigLoadResult.getOkValue();


    Controller.Builder controllerBuilder = new Controller.Builder();

    assert gameWorldConfig.getLocationConfigs() != null;
    assert gameWorldConfig.getLocationConfigs().size() > 0 : "There must be at least one location config specified";

    for (LocationConfig locationConfig : gameWorldConfig.getLocationConfigs()) {

      List<GameObject> gameObjects = loadGameObjectsForLocation(locationConfig);
      List<GameObjectView> gameObjectViews = loadGameObjectViewsForLocation(locationConfig);

      registerGameObjectViewsToModel(gameObjects, gameObjectViews);

      LocationModel model = new LocationModel.Builder()
          .setTag(locationConfig.getTag())
          .setBounds(new Point2D(locationConfig.getWidth(), locationConfig.getHeight()))
          .setGameObjects(gameObjects)
          .build();

      LocationView view = loadLocationViewFromConfig(locationConfig);

      assert view != null;

      gameObjectViews.forEach(view::addChild);
      model.addOnLocationModelStateChangeObserver(view);

      controllerBuilder
          .addViewForTag(locationConfig.getTag(), view)
          .addModelForTag(locationConfig.getTag(), model)
          .registerToViews(gameObjectViews);

    }

    // Player is created separately
    // TODO: consider moving it to separate method
    Player player = (Player) GameObjectFactory.fromConfig(gameWorldConfig.getPlayerConfig());
    GameObjectView playerView = GameObjectViewFactory.fromConfig(gameWorldConfig.getPlayerConfig());
    PlayerController playerController = new PlayerController(player, playerView);

    controllerBuilder.setPlayerController(playerController);


    Controller controller = controllerBuilder.build();

    Game.Builder gameBuilder = new Game.Builder();
    Game game = gameBuilder
        .setController(controller)
        .setOnStartAction(new LocationChangeAction(gameWorldConfig.getRootLocation(), player.getPosition()))
        .build();

    return Result.ok(game);
  }

  public static List<GameObject> loadGameObjectsForLocation(LocationConfig config) {
    return GameObjectFactory.fromConfigList(config.getObjects());
  }

  public static List<GameObjectView> loadGameObjectViewsForLocation(LocationConfig config) {
    return GameObjectViewFactory.fromConfigList(config.getObjects());
  }

  public static void registerGameObjectViewsToModel(List<GameObject> gameObjects,
                                                    List<GameObjectView> gameObjectViews) {
    assert gameObjects.size() == gameObjectViews.size() : "Arrays must be of the same length!";

    Iterator<GameObject> gameObjectIterator = gameObjects.iterator();
    Iterator<GameObjectView> gameObjectViewIterator = gameObjectViews.iterator();

    // we asserted earlier that both lists have the same length thus we don't
    // need to check .hasNext() for both lists
    while (gameObjectIterator.hasNext()) {
      GameObject gameObject = gameObjectIterator.next();
      GameObjectView gameObjectView = gameObjectViewIterator.next();

      // registration
      gameObject.addGameObjectStateChangeObserver(gameObjectView);
      gameObjectView.bindToGameObject(gameObject);
    }
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
}
