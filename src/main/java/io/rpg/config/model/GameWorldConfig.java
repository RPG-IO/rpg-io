package io.rpg.config.model;

import io.rpg.config.model.LocationConfig;

import java.util.ArrayList;
import java.util.List;

public class GameWorldConfig {
  private String tag;

  private ArrayList<String> locations;

  private ArrayList<LocationConfig> locationConfigs;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private GameWorldConfig() {
    locations = new ArrayList<>();
    locationConfigs = new ArrayList<>();
  }

  public String getTag() {
    return tag;
  }

  public List<String> getLocations() {
    return locations;
  }

  public ArrayList<LocationConfig> getLocationConfigs() {
    return locationConfigs;
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

  public void addLocationConfig(LocationConfig locationConfig) {
    locationConfigs.add(locationConfig);
  }
}
