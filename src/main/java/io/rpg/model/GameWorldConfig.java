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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("\n{\n").append("\ttag: ").append(tag).append('\n');
    locations.forEach(location -> {
      builder.append("\tlocation-tag: ").append(location).append('\n');
    });
    return builder.append("}\n").toString();
  }
}
