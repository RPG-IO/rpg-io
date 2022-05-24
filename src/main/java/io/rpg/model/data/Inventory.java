package io.rpg.model.data;

import io.rpg.model.object.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

  public List<GameObject> items;

  public Inventory() {
    items = new ArrayList<>();
  }

  public void add(GameObject object) {
    items.add(object);
  }
}
