package io.rpg.view;

import io.rpg.model.object.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {
  GameObject collectibleGameObject;

  public InventoryGameObjectView(GameObject collectibleGameObject, String assetPath) {
    this.collectibleGameObject = collectibleGameObject;
    Image image = new Image(GameObjectView.resolvePathToJFXFormat(assetPath));
    setImage(image);
  }
}
