package io.rpg.controller;

import io.rpg.view.popups.TextPopup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PopupController {

  private final Stage popupStage = new Stage(StageStyle.TRANSPARENT);

  public PopupController() {
    // close popup after clicking aside
    popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        popupStage.close();
      }
    });
  }

  public void openTextPopup(String text, int x, int y){
    TextPopup popupScene = new TextPopup(text);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);

    popupScene.hideStageOnButton(popupStage);
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    openTextPopup("You earned " + pointsCount + " points!", x, y);
  }


  public void hidePopup() {
    popupStage.hide();
  }

}

