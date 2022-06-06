package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  protected void emitAction(Action action) {
    action.getBeforeAction().ifPresent(a -> consumer.consumeAction(a));
    consumer.consumeAction(action);
    action.getAfterAction().ifPresent(a -> consumer.consumeAction(a));
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
