package io.rpg.view;

import io.rpg.model.data.Inventory;
import io.rpg.model.data.Position;
import io.rpg.model.object.Player;
import io.rpg.viewmodel.InventoryPopupViewModel;
import io.rpg.viewmodel.TextPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryPopup extends Scene {
  final int PADDING_LEFT = 25;
  final int PADDING_TOP = 20;
  @FXML
  private Label label;

  @FXML
  private Label strengthLabel;

  @FXML
  private Label pointsLabel;

  public InventoryPopup(Inventory inventory, Player player) {

    super(new Group(), Color.TRANSPARENT);
    Group group = new Group();
    //TODO: load asset path from config
    ImageView imageView = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/popup-background.png"));
    imageView.setX(0);
    imageView.setY(0);
    this.pointsLabel = new Label();
    this.pointsLabel.setText("Points :" + String.valueOf(player.getPoints()));
    this.pointsLabel.setLayoutX(325);
    this.pointsLabel.setLayoutY(150);
    this.pointsLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
    this.strengthLabel=new Label();
    this.strengthLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
    this.strengthLabel.setText("Strength :"+String.valueOf(player.getStrength()));
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

    for (int i = 0; i < inventory.items.size(); i++) {
//            String assetPath=inventory.items.get(i).getAssetPath();
//            to display objects in the menu
//            wrapperClass to store information about object which we display
      InventoryGameObjectView imageGameObjectView = new InventoryGameObjectView(inventory.items.get(i));

      imageGameObjectView.setX(i * 50 + PADDING_LEFT);
      imageGameObjectView.setY(0 + PADDING_TOP);
      imageGameObjectView.setOnMouseEntered(event -> {
        InventoryGameObjectView src = (InventoryGameObjectView) event.getSource();
//                System.out.println("over the item "+src.collectibleGameObject.getAssetPath());
        label.setText(src.collectibleGameObject.getAssetPath());
      });

      imageGameObjectView.setOnMouseExited(event -> {
        label.setText("");
      });

      imageGameObjectView.setOnMouseClicked(event -> {

        System.out.println("Object clicked");
        InventoryGameObjectView src = (InventoryGameObjectView) event.getSource();
        System.out.println();
      });
      group.getChildren().add(imageGameObjectView);

    }
    this.setRoot(group);
    this.setFill(Color.TRANSPARENT);
  }

}
