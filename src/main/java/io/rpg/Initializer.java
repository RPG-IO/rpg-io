package io.rpg;

import com.kkafara.rt.Result;
import io.rpg.config.ConfigLoader;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import io.rpg.controller.Controller;
import io.rpg.controller.PlayerController;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Player;
import io.rpg.util.GameObjectFactory;
import io.rpg.util.GameObjectViewFactory;
import io.rpg.view.GameObjectView;
import io.rpg.view.LocationView;
import io.rpg.view.popups.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Initializer {
  private Path pathToConfigDir;
  private ConfigLoader configLoader;

  private final Logger logger;

  public Initializer(@NotNull String pathToConfigDir) {
    this.configLoader = new ConfigLoader(pathToConfigDir);
    this.logger = LogManager.getLogger(Initializer.class);
  }

  public Result<Game, Exception> initialize() {
    Result<GameWorldConfig, Exception> gameWorldConfigLoadResult = configLoader.load();

    if (gameWorldConfigLoadResult.isErr()) {
      gameWorldConfigLoadResult.getErrOpt().ifPresentOrElse(
          ex -> logger.error(ex.getMessage()),
          () -> logger.error("Unknown error returned from config loader")
      );
      return Result.err(gameWorldConfigLoadResult.getErr());
    } else if (gameWorldConfigLoadResult.isOkValueNull()) {
      logger.error("ConfigLoader fetched null GameWorldConfig");
      return Result.err(new RuntimeException("ConfigLoader fetched null GameWorldConfig"));
    }

    GameWorldConfig gameWorldConfig = gameWorldConfigLoadResult.getOk();

    // set assets for popups
    QuestionPopup.setBackgroundPath(gameWorldConfig.getQuizPopupBackground());
    TextPopup.setBackgroundPath(gameWorldConfig.getTextPopupBackground());
    TextImagePopup.setBackgroundPath(gameWorldConfig.getTextImagePopupBackground());
    TextPopup.setButtonPath(gameWorldConfig.getTextPopupButton());
    TextImagePopup.setButtonPath(gameWorldConfig.getTextImagePopupButton());
    DialoguePopup.setBackgroundPath(gameWorldConfig.getDialoguePopupBackground());
    DialoguePopup.setNpcFramePath(gameWorldConfig.getNpcFrame());
    InventoryPopup.setBackgroundPath(gameWorldConfig.getInventoryPopupBackground());

    Controller.Builder controllerBuilder = new Controller.Builder();

    assert gameWorldConfig.getLocationConfigs() != null;
    assert gameWorldConfig.getLocationConfigs().size() > 0 : "There must be at least one location config specified";

    for (LocationConfig locationConfig : gameWorldConfig.getLocationConfigs()) {
      assert locationConfig != null;

      List<GameObject> gameObjects = loadGameObjectsForLocation(locationConfig);
      List<GameObjectView> gameObjectViews = loadGameObjectViewsForLocation(locationConfig);

      registerGameObjectViewsToModel(gameObjects, gameObjectViews);
      LocationModel model = new LocationModel.Builder()
          .setTag(locationConfig.getTag())
          .setBounds(new Point2D(locationConfig.getWidth(), locationConfig.getHeight()))
          .setGameObjects(gameObjects)
          .setDirectionToLocationMap(locationConfig.getDirectionToLocationMap())
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
        .setOnStartAction(new LocationChangeAction(gameWorldConfig.getRootLocation(), player.getPosition(), null))
        .build();

    return Result.ok(game);
  }

  public static List<GameObject> loadGameObjectsForLocation(LocationConfig config) {
    return GameObjectFactory.fromConfigs(config.getObjects());
  }

  public static List<GameObjectView> loadGameObjectViewsForLocation(LocationConfig config) {
    return GameObjectViewFactory.fromConfigs(config.getObjects());
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
