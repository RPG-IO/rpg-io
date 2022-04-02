package io.rpg.model.location;


import io.rpg.model.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class LocationConfig {
  private String tag;

  private ArrayList<GameObject> objects;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private LocationConfig() {
  }

  public String getTag() {
    return tag;
  }

  public List<GameObject> getObjects() {
    return objects;
  }
}
