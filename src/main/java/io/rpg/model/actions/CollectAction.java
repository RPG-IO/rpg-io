package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.Nullable;

public class CollectAction extends BaseAction {
  private final String assetPath;

  public CollectAction(String assetPath, @Nullable Condition condition) {
    super(condition);
    this.assetPath = assetPath;
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
}

