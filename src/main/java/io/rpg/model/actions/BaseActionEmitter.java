package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  protected void emitAction(Action action) {
    if (action.getBeforeAction() != null) {
      consumer.consumeAction(action.getBeforeAction());
    }

    consumer.consumeAction(action);

    if (action.getAfterAction() != null) {
      consumer.consumeAction(action.getAfterAction());
    }
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
