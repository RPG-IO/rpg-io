package io.rpg.viewmodel;

import io.rpg.model.object.GameObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LocationViewModel implements Initializable {
  @FXML
  private ImageView mapImageView;
  @FXML
  private Pane foregroundPane, contentPane;
  @FXML
  private HBox parent;

  public LocationViewModel() {

  }

  public void setBackground(Image background) {
    this.mapImageView.imageProperty().setValue(background);
  }

  public HBox getParent() {
    return parent;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("INITIALIZE");
    mapImageView.imageProperty().addListener((property, oldImg, newImg) -> {
      System.out.println("Map image view changing");
      contentPane.setPrefWidth(newImg.getWidth());
      contentPane.setPrefHeight(newImg.getHeight());

      foregroundPane.setPrefWidth(newImg.getWidth());
      foregroundPane.setPrefHeight(newImg.getHeight());

      mapImageView.setFitWidth(newImg.getWidth());
      mapImageView.setFitHeight(newImg.getHeight());
    });
  }

  public void addChild(ImageView child) {
    contentPane.getChildren().add(child);
  }
}
