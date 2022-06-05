package io.rpg.controller;

import io.rpg.model.object.Question;
import io.rpg.view.popups.DialoguePopup;
import io.rpg.view.popups.QuestionPopup;
import io.rpg.view.popups.TextImagePopup;
import io.rpg.view.popups.TextPopup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayDeque;
import java.util.Queue;

public class PopupController {

  private final Stage popupStage = new Stage(StageStyle.TRANSPARENT);
  private final Image coinImage = new Image("file:assets/coin.png");
  private final Queue<Runnable> methods = new ArrayDeque<>();

  public PopupController() {
    // close popup after clicking aside
    popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
      if (!isNowFocused) {
        popupStage.close();
      }
    });
  }

  public void openTextPopup(String text, int x, int y) {
    TextPopup popupScene = new TextPopup(text);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);

    popupScene.setButtonCallback(event -> hidePopup());
  }

  public void openTextImagePopup(String text, Image image, int x, int y) {
    TextImagePopup popupScene = new TextImagePopup(text, image);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);

    popupScene.setButtonCallback(event -> hidePopup());
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    openTextImagePopup("You earned " + pointsCount + " points!", coinImage, x, y);
  }

  public void openQuestionPopup(Question question, int x, int y, Runnable successCallback, Runnable failureCallback) {
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
  
  public void openDialoguePopup(String text, Image npcImage, int x, int y) {
    DialoguePopup popupScene = new DialoguePopup(text, npcImage);
    popupStage.setScene(popupScene);

    popupStage.show();

    popupStage.setX(x - popupScene.getWidth() / 2);
    popupStage.setY(y - popupScene.getHeight() / 2);

    popupScene.setCloseButtonCallback(event -> hidePopup());
  }

  public void hidePopup() {
    popupStage.hide();
    if (!methods.isEmpty()) {
      methods.remove().run();
    }
  }

  public void addMethodToQueue(Runnable method) {
    methods.add(method);
  }
}
