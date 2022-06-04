package io.rpg.model.actions;

import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.Nullable;

/**
 * A marker interface for action classes.
 */
public interface Action {
  Action VOID = new Action() {
    @Override
    public void acceptActionEngine(ActionEngine engine) {
      /* noop */
    }

    @Override
    public void setEmitter(GameObject emitter) {
      /* noop */
    }

    @Override
    public @Nullable GameObject getEmitter() {
      return null;
    }

    @Override
    public void setBeforeAction(Action action) {
      /* noop */
    }

    @Override
    public void setAfterAction(Action action) {
      /* noop */
    }

    @Override
    public @Nullable Action getBeforeAction() {
      return null;
    }

    @Override
    public @Nullable Action getAfterAction() {
      return null;
    }
  };

  void acceptActionEngine(final ActionEngine engine);

  void setEmitter(GameObject emitter);

  @Nullable
  GameObject getEmitter();

  void setBeforeAction(Action action);

  void setAfterAction(Action action);

  @Nullable
  Action getBeforeAction();

  @Nullable
  Action getAfterAction();
}
