package io.rpg.model;

import io.rpg.model.location.LocationConfig;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameWorldConfig {
  private String tag;

  private ArrayList<String> locations;

  private ArrayList<LocationConfig> locationConfigs;

  private LinkedHashMap<String, LocationConfig> tagToLocationConfigMap;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private GameWorldConfig() {
    tagToLocationConfigMap = new LinkedHashMap<>();
  }


  public String getTag() {
    return tag;
  }

  public List<String> getLocations() {
    return locations;
  }

  public LinkedHashMap<String, LocationConfig> getTagToLocationConfigMap() {
    return tagToLocationConfigMap;
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

  public void addLocationConfigForTag(String locationTag, LocationConfig locationConfig) {
    tagToLocationConfigMap.put(locationTag, locationConfig);
  }
}
