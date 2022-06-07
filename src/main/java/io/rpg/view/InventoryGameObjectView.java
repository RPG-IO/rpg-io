package io.rpg.view;

import io.rpg.util.PathUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InventoryGameObjectView extends ImageView {

  public final String description;

  public final String tag;

  public InventoryGameObjectView(String assetPath, String description, String tag) {
    Image image = new Image(PathUtils.resolvePathToJFXFormat(assetPath));
    setImage(image);

    this.description = description;
    this.tag = tag;
  }
}
