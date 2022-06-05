package io.rpg.model.data;

import io.rpg.model.object.GameObject;
import io.rpg.view.InventoryGameObjectView;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

  public List<InventoryGameObjectView> items;

  public Inventory() {
    items = new ArrayList<>();
  }

  public void add(InventoryGameObjectView object) {
    items.add(object);
  }
}
