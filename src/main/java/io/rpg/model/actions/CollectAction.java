package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.Nullable;

public class CollectAction extends BaseAction {
  private final String assetPath;
  private final String description;

  public CollectAction(String assetPath, String description, @Nullable Condition condition) {
    super(condition);
    this.assetPath = assetPath;
    this.description = description;
  }

  public GameObject getOwner() {
    return getEmitter();
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }

  public String getAssetPath() {
    return assetPath;
  }

  public String getDescription() {
    return description;
  }
}

