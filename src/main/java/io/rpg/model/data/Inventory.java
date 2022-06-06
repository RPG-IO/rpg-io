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

  public List<InventoryGameObjectView> getItems() {
    return items;
  }


  public boolean containsItemForTag(String itemTag) {
    if (itemTag == null) {
      return false;
    }

    for (InventoryGameObjectView item : items) {
      if (itemTag.equals(item.tag)) {
        return true;
      }
    }
    return false;
  }
}
