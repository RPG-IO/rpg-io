package io.rpg.model.location;


import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LocationConfig {
  @Nullable
  private String tag;

  private ArrayList<GameObject> objects;

  // This class is not meant to be instantiated
  // by hand. Only Gson should be able to do so
  private LocationConfig() {
  }

  @Nullable
  public String getTag() {
    return tag;
  }

  public List<GameObject> getObjects() {
    return objects;
  }

//  @Override
//  public String toString() {
////    StringBuilder builder
//
//  }
}
