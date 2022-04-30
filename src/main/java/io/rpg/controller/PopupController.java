package io.rpg.controller;

import io.rpg.view.popups.PointsEarnedPopup;
import io.rpg.view.popups.TextPopup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PopupController {

  private final Stage popupStage = new Stage(StageStyle.TRANSPARENT);
  private PointsEarnedPopup pointsPopupScene;

  public PopupController() {
    try {
      pointsPopupScene = new PointsEarnedPopup();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // close popup after clicking aside
    popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        popupStage.close();
      }
    });
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    pointsPopupScene.setPointsCount(pointsCount);
    popupStage.setScene(pointsPopupScene);

    popupStage.show();

    popupStage.setX(x - pointsPopupScene.getWidth() / 2);
    popupStage.setY(y - pointsPopupScene.getHeight() / 2);
  }

  public void openTextPopup(String text, int x, int y){
    TextPopup popupScene = new TextPopup(text);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);
  }

  public void hidePopup() {
    popupStage.hide();
  }

}

