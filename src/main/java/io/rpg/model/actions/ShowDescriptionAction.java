package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import javafx.scene.image.Image;

/**
 * Class for storing local data needed to perform a show description action.
 */
public class ShowDescriptionAction extends ConditionalAction {
  public final String description;
  public final Image image;

  public ShowDescriptionAction(String description, Image image, Condition condition) {
    super(condition);
    this.description = description;
    this.image = image;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
