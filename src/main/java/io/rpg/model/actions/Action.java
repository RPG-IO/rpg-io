package io.rpg.model.actions;

/**
 * A marker interface for action classes.
 */
public interface Action {
  Action VOID = new Action() {
    @Override
    public void acceptActionEngine(ActionEngine engine) {
      /* noop */
    }
  };

  void acceptActionEngine(final ActionEngine engine);
}
