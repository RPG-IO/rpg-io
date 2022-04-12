package io.rpg.view;

public interface IObservable<T> {
  void addListener(final T listener);

  void removeListener(final T listener);
}
