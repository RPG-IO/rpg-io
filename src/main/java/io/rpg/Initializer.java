package io.rpg;

import io.rpg.config.ConfigLoader;
import io.rpg.controller.Controller;
import io.rpg.config.model.GameWorldConfig;
import io.rpg.config.model.LocationConfig;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.config.model.GameObjectConfig;
import io.rpg.view.LocationView;
import javafx.stage.Stage;
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

  public Initializer(@NotNull String pathToConfigDir, Stage mainStage) {
    configLoader = new ConfigLoader(pathToConfigDir);
    this.mainStage = mainStage;
  }



  public Game getWorld() {
    GameWorldConfig worldConfig = configLoader.load();


    // todo: apply builder pattern
    Game game = new Game();

    Controller controller = new Controller();


    LinkedHashMap<String, LocationModel> tagToLocationModelMap = new LinkedHashMap<>();
    LinkedHashMap<String, LocationView> tagToLocationViewMap = new LinkedHashMap<>();

    assert worldConfig.getLocationConfigs() != null;
    for (LocationConfig locationConfig : worldConfig.getLocationConfigs()) {
      LocationModel model = loadLocationModelFromConfig(locationConfig);
      LocationView view = buildViewFromModel(locationConfig);

      assert view != null;
      view.addListener(controller);

      tagToLocationModelMap.put(locationConfig.getTag(), model);
      // TODO: Set correct parent
      tagToLocationViewMap.put(locationConfig.getTag(), view);
    }

    // TODO: recognize root location
    String rootTag = "location-1";

    System.out.println("ROOT VIEW");
    System.out.println(tagToLocationViewMap.get(rootTag));

    controller.setTagToLocationModelMap(tagToLocationModelMap);
    controller.setTagToLocationViewMap(tagToLocationViewMap);
    controller.setModel(tagToLocationModelMap.get(rootTag));
    controller.setView(tagToLocationViewMap.get(rootTag));



    game.setController(controller);

    return game;
  }

  @Nullable
  public static LocationView buildViewFromModel(LocationConfig config) {
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
