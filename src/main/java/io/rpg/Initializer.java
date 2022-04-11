package io.rpg;

import io.rpg.config.ConfigLoader;
import io.rpg.controller.LocationController;
import io.rpg.model.GameWorldConfig;
import io.rpg.model.location.LocationConfig;
import io.rpg.model.location.LocationModel;
import io.rpg.view.LocationView;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Initializer {
  private Path pathToConfigDir;
  private ConfigLoader configLoader;

  public Initializer(@NotNull String pathToConfigDir) {
    configLoader = new ConfigLoader(pathToConfigDir);
  }


  public World getWorld() {
    GameWorldConfig worldConfig = configLoader.load();

    LinkedHashMap<String, LocationConfig> locationConfigLinkedHashMap = worldConfig.getTagToLocationConfigMap();

    // todo: apply builder pattern
    World world = new World();

    for (Map.Entry<String, LocationConfig> entry : locationConfigLinkedHashMap.entrySet()) {
      LocationModel model = LocationModel.fromConfig(entry.getValue());
      LocationController controller = LocationController.fromConfig(entry.getValue());
      LocationView view = LocationView.fromConfig(entry.getValue());

      controller.setModel(model);
      controller.setView(view);;


      world.addLocationController(controller);

      if (controller.isRoot()) {
        world.setCurrentLocationController(controller);
      }
    }
  }
}
