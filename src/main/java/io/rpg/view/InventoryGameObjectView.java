package io.rpg.view;

import io.rpg.model.object.CollectibleGameObject;
import io.rpg.model.object.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {
  GameObject collectibleGameObject;

  public InventoryGameObjectView(GameObject collectibleGameObject) {
    this.collectibleGameObject = collectibleGameObject;
    Image image = new Image(GameObjectView.resolvePathToJFXFormat("assets/key.png"));
    setImage(image);
  }
}
