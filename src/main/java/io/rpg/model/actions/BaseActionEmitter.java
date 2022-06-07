package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  protected void emitAction(Action action) {
    action.getBeforeAction().ifPresent(a -> emitAction(action));
    consumer.consumeAction(action);
    action.getAfterAction().ifPresent(a -> emitAction(action));
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
