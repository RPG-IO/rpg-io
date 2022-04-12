package io.rpg.view;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public interface IOnKeyPressedListener {
  void onKeyPressed(Scene source, KeyEvent event);
}
