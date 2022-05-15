package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  protected void emitAction(Action action) {
    consumer.consumeAction(action);
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
