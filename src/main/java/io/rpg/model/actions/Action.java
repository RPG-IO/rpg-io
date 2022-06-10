package io.rpg.model.actions;

import io.rpg.model.object.GameObject;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    public Optional<Action> getBeforeAction() {
      return Optional.empty();
    }

    @Override
    public Optional<Action> getAfterAction() {
      return Optional.empty();
    }
  };

  void acceptActionEngine(final ActionEngine engine);

  void setEmitter(GameObject emitter);

  @Nullable
  GameObject getEmitter();

  void setBeforeAction(Action action);

  void setAfterAction(Action action);

  Optional<Action> getBeforeAction();

  Optional<Action> getAfterAction();
}
