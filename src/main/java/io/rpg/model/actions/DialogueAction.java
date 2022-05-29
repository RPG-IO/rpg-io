package io.rpg.model.actions;

import javafx.scene.image.Image;

/**
 * Class for storing local data needed to perform a dialogue action.
 */
public class DialogueAction implements Action {
  public final String text;
  public final Image image;

  public DialogueAction(String text, Image image) {
    this.text = text;
    this.image = image;
  }

}
