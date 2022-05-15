package io.rpg.model.actions;

public abstract class BaseActionEmitter implements ActionEmitter {
  private ActionConsumer consumer = (a) -> {};

  @Override
  public void setActionConsumer(ActionConsumer actionConsumer) {
    this.consumer = actionConsumer;
  }
}
