package io.rpg.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {

  public final String description;

  public InventoryGameObjectView(String assetPath, String description) {
    Image image = new Image(GameObjectView.resolvePathToJFXFormat(assetPath));
    setImage(image);

    this.description = description;
  }
}
