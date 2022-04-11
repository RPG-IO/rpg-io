package io.rpg;

import io.rpg.controller.LocationController;
import io.rpg.gui.model.LocationModel;
import io.rpg.view.LocationView;
import javafx.scene.Scene;

import java.util.LinkedHashMap;
import java.util.List;

public class World {
  // mapa tag lokacji na lokację
  private LinkedHashMap<String, LocationModel> locationTagToModelMap;
  private LocationController currentLocationController;



  public World() {

  }

  public void addLocationController(LocationController controller) {
    locationControllers.add(controller);
  }

  public void setLocationControllerForLocationTag(String tag) {
    // tutaj przyda sie hashmapa: nazwa lokacji -> kontroller
  }


  public LocationView getWorldView() {
    return currentLocationController.getView();
  }

  public void setCurrentLocationController(LocationController currentLocationController) {
    this.currentLocationController = currentLocationController;
  }
}
