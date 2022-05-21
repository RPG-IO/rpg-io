package io.rpg.model.actions;

import javafx.scene.image.Image;

/**
 * Class for storing local data needed to perform a show description action.
 */

public class ShowDescriptionAction implements Action {
  public final String description;
  public final Image image;

  public ShowDescriptionAction(String description, Image image) {
    this.description = description;
    this.image = image;
  }
}
