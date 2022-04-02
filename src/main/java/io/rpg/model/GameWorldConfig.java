package io.rpg.model;

import java.util.ArrayList;
import java.util.List;

public class GameWorldConfig {
  private String tag;

  private ArrayList<String> locations;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private GameWorldConfig() {}

  public String getTag() {
    return tag;
  }

  public List<String> getLocations() {
    return locations;
  }
}
