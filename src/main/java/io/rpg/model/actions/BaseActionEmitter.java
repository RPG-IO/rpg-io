package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  protected void emitAction(Action action) {
    if (action.equals(Action.VOID)) {
      return;
    }
    action.getBeforeAction().ifPresent(this::emitAction);
    consumer.consumeAction(action);
    action.getAfterAction().ifPresent(this::emitAction);
  }

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
