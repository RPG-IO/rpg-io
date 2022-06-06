package io.rpg.controller;

import io.rpg.model.object.Player;
import io.rpg.model.object.Question;
import io.rpg.view.popups.BattleReflexPopup;
import io.rpg.view.popups.DialoguePopup;
import io.rpg.view.popups.QuestionPopup;
import io.rpg.model.data.Inventory;
import io.rpg.view.popups.InventoryPopup;
import io.rpg.view.popups.TextImagePopup;
import io.rpg.view.popups.TextPopup;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.function.BiConsumer;

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

  public void openTextPopup(String text, int x, int y) {
    TextPopup popupScene = new TextPopup(text);
    popupStage.setScene(popupScene);

    popupScene.setButtonCallback(event -> popupStage.hide());

    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });

    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openTextImagePopup(String text, Image image, int x, int y) {
    TextImagePopup popupScene = new TextImagePopup(text, image);
    popupStage.setScene(popupScene);

    popupScene.setButtonCallback(event -> popupStage.hide());
    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });
    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    openTextImagePopup("You earned " + pointsCount + " points!", coinImage, x, y);
  }

  public void openInventoryPopup(Inventory inventory, int x, int y, Player player) {
    InventoryPopup popupScene = new InventoryPopup(inventory, player);
    popupStage.setScene(popupScene);

    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });

    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openBattleReflexPopup(int pointsPerSecond, BiConsumer<Boolean, Integer> callback, int x, int y){
    BattleReflexPopup popupScene = new BattleReflexPopup(pointsPerSecond, callback);
    popupScene.setCloseButtonActionListener((event) -> hidePopup());
    popupStage.setScene(popupScene);
    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });
    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openQuestionPopup(Question question, int x, int y, Runnable successCallback, Runnable failureCallback) {
    QuestionPopup popupScene = new QuestionPopup(question);
    popupScene.setSuccessCallback(successCallback);
    popupScene.setFailureCallback(failureCallback);
    popupStage.setScene(popupScene);
    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });
    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openQuestionPopup(Question question, int x, int y) {
    QuestionPopup popupScene = new QuestionPopup(question);
    popupStage.setScene(popupScene);
    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });
    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }
  
  public void openDialoguePopup(String text, Image npcImage, int x, int y) {
    DialoguePopup popupScene = new DialoguePopup(text, npcImage);
    popupStage.setScene(popupScene);

    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });

    popupScene.setCloseButtonCallback(event -> popupStage.hide());
    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }


  public void hidePopup() {
    popupStage.hide();
  }
}
