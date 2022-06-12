package io.rpg.controller;

import io.rpg.model.object.Player;
import io.rpg.model.object.Question;
import io.rpg.util.PathUtils;
import io.rpg.view.popups.BattleReflexPopup;
import io.rpg.view.popups.DialoguePopup;
import io.rpg.view.popups.QuestionPopup;
import io.rpg.model.data.Inventory;
import io.rpg.view.popups.InventoryPopup;
import io.rpg.view.popups.TextImagePopup;
import io.rpg.view.popups.TextPopup;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.function.BiConsumer;

public class PopupController {

  private final Stage popupStage = new Stage(StageStyle.TRANSPARENT);
  private final Image coinImage = new Image(PathUtils.resolvePathToJFXFormat(PathUtils.resolvePathToAsset("coin.png").get()));

  private void openPopup(Scene popupScene, int x, int y) {
    popupStage.setScene(popupScene);

    popupStage.onShownProperty().setValue(event -> {
      popupStage.setX(x - popupScene.getWidth() / 2);
      popupStage.setY(y - popupScene.getHeight() / 2);
    });

    if (!popupStage.isShowing()) {
      popupStage.showAndWait();
    }
  }

  public void openTextPopup(String text, int x, int y) {
    TextPopup popupScene = new TextPopup(text);
    popupScene.setButtonCallback(event -> popupStage.hide());
    openPopup(popupScene, x, y);
  }

  public void setOwner(Window window) {
    popupStage.initOwner(window);
    popupStage.initModality(Modality.WINDOW_MODAL);
  }

  public void openTextImagePopup(String text, Image image, int x, int y) {
    TextImagePopup popupScene = new TextImagePopup(text, image);
    popupScene.setButtonCallback(event -> popupStage.hide());
    openPopup(popupScene, x, y);
  }

  public void openPointsPopup(int pointsCount, int x, int y) {
    openTextImagePopup("You earned " + pointsCount + " points!", coinImage, x, y);
  }

  public void openInventoryPopup(Inventory inventory, int x, int y, Player player) {
    InventoryPopup popupScene = new InventoryPopup(inventory, player);
    popupScene.setCloseButtonCallback(event -> popupStage.hide());
    openPopup(popupScene, x, y);
  }

  public void openBattleReflexPopup(int pointsPerSecond, BiConsumer<Boolean, Integer> callback, int x, int y) {
    BattleReflexPopup popupScene = new BattleReflexPopup(pointsPerSecond, callback);
    popupScene.setCloseButtonActionListener((event) -> hidePopup());
    openPopup(popupScene, x, y);
  }

  public void openQuestionPopup(Question question, int x, int y, BiConsumer<Boolean, Integer> callback, int reward) {
    QuestionPopup popupScene = new QuestionPopup(question, callback, reward);
    popupScene.setOkButtonCallback((event) -> hidePopup());
    openPopup(popupScene, x, y);
  }

  public void openDialoguePopup(String text, Image npcImage, int x, int y) {
    DialoguePopup popupScene = new DialoguePopup(text, npcImage);
    popupScene.setCloseButtonCallback(event -> popupStage.hide());
    openPopup(popupScene, x, y);
  }

  public void hidePopup() {
    popupStage.hide();
  }
}
