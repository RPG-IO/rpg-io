package io.rpg.model.actions;

import io.rpg.model.object.GameObject;

public class CollectAction implements Action {
  private GameObject owner;

  public void setOwner(GameObject owner) {
    this.owner = owner;
  }

  public GameObject getOwner() {
    return owner;
  }
}

