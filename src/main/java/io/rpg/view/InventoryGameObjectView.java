package io.rpg.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {

  public final String description;

  public final String tag;

  public InventoryGameObjectView(String assetPath, String description, String tag) {
    Image image = new Image(GameObjectView.resolvePathToJFXFormat(assetPath));
    setImage(image);

    this.description = description;
    this.tag = tag;
  }
}
