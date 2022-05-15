package io.rpg.controller;

import io.rpg.model.object.Question;
import io.rpg.view.popups.QuestionPopup;
import io.rpg.view.popups.TextImagePopup;
import io.rpg.view.popups.TextPopup;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PopupController {

  private final Stage popupStage = new Stage(StageStyle.TRANSPARENT);
  private final Image coinImage = new Image("file:assets/coin.png");

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

    popupScene.setButtonCallback(event -> popupStage.hide());
  }

  public void openTextImagePopup(String text, Image image, int x, int y){
    TextImagePopup popupScene = new TextImagePopup(text, image);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);

    popupScene.setButtonCallback(event -> popupStage.hide());
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    openTextImagePopup("You earned " + pointsCount + " points!", coinImage, x, y);
  }

  public void openQuestionPopup(Question question, int x, int y, EventHandler<? super MouseEvent> successCallback, EventHandler<? super MouseEvent> failureCallback) {
    QuestionPopup popupScene = new QuestionPopup(question);
    popupScene.setSuccessCallback(successCallback);
    popupScene.setFailureCallback(failureCallback);
    popupStage.setScene(popupScene);
    popupStage.show();
    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);
  }

  public void openQuestionPopup(Question question, int x, int y) {
    QuestionPopup popupScene = new QuestionPopup(question);
    popupStage.setScene(popupScene);
    popupStage.show();
    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);
  }


  public void hidePopup() {
    popupStage.hide();
  }

}

