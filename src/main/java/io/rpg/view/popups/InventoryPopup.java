package io.rpg.view.popups;

import io.rpg.model.data.Inventory;
import io.rpg.model.object.Player;
import io.rpg.util.PathUtils;
import io.rpg.view.InventoryGameObjectView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class InventoryPopup extends Scene {
  final int PADDING_LEFT = 25;
  final int PADDING_TOP = 20;
  private final Button closeButton;
  @FXML
  private Label label;

  @FXML
  private Label strengthLabel;

  @FXML
  private Label pointsLabel;

  private static String backgroundPath;

  public InventoryPopup(Inventory inventory, Player player) {

    super(new Group(), Color.TRANSPARENT);
    Group group = new Group();

    ImageView imageView = new ImageView(backgroundPath);
    imageView.setX(0);
    imageView.setY(0);

    closeButton = new Button();
    closeButton.setLayoutX(425);
    closeButton.setLayoutY(36);
    closeButton.setMnemonicParsing(false);
    closeButton.setPrefHeight(26.);
    closeButton.setPrefWidth(26.);
    closeButton.setStyle("-fx-background-color: transparent;");

    ImageView closeButtonImageView = new ImageView(PathUtils.resolvePathToJFXFormat(PathUtils.resolvePathToAsset("close-button.png").get()));
    closeButtonImageView.setFitHeight(18.);
    closeButtonImageView.setFitWidth(16.);
    closeButtonImageView.setPickOnBounds(true);
    closeButtonImageView.setPreserveRatio(true);

    closeButton.setGraphic(closeButtonImageView);

    this.pointsLabel = new Label();
    this.pointsLabel.setText("Points :" + player.getPoints());
    this.pointsLabel.setLayoutX(325);
    this.pointsLabel.setLayoutY(150);
    this.pointsLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);

    this.strengthLabel = new Label();
    this.strengthLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
    this.strengthLabel.setText("Strength :" + player.getStrength());
    this.strengthLabel.setLayoutX(325);
    this.strengthLabel.setLayoutY(200);

    label = new Label();
    label.setLayoutX(300);
    label.setLayoutY(100);
    label.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);

    group.getChildren().add(imageView);
    group.getChildren().add(label);
    group.getChildren().add(pointsLabel);
    group.getChildren().add(strengthLabel);
    group.getChildren().add(closeButton);

    for (int i = 0; i < inventory.items.size(); i++) {
      InventoryGameObjectView inventoryItem = inventory.items.get(i);

      inventoryItem.setX(i * 50 + PADDING_LEFT);
      inventoryItem.setY(PADDING_TOP);

      inventoryItem.setOnMouseEntered(event -> label.setText(inventoryItem.description));
      inventoryItem.setOnMouseExited(event -> label.setText(""));

      group.getChildren().add(inventoryItem);
    }

    this.setRoot(group);
    this.setFill(Color.TRANSPARENT);
  }

  public static void setBackgroundPath(String backgroundPath) {
    InventoryPopup.backgroundPath = backgroundPath;
  }

  public void setCloseButtonCallback(EventHandler<? super MouseEvent> callback) {
    closeButton.setOnMouseClicked(callback);
  }
}
