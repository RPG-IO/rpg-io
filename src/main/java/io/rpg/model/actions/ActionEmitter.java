package io.rpg.model.actions;

public interface ActionEmitter {
  void emitAction(Action action);

  void setActionConsumer(ActionConsumer actionConsumer);
}
