package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import javafx.scene.image.Image;

/**
 * Class for storing local data needed to perform a dialogue action.
 */
public class DialogueAction extends ConditionalAction {
  public final String text;
  public final Image image;

  public DialogueAction(String text, Image image, Condition condition) {
    super(condition);
    this.text = text;
    this.image = image;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
