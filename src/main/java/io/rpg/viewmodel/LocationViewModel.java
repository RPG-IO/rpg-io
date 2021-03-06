package io.rpg.viewmodel;

import io.rpg.model.object.GameObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

  private final Logger logger;

  public LocationViewModel() {
    logger = LogManager.getLogger(LocationViewModel.class);
  }

  public void setBackground(Image background) {
    this.mapImageView.imageProperty().setValue(background);
  }

  public HBox getParent() {
    return parent;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    logger.info("Initializing LocationViewModel");
    mapImageView.imageProperty().addListener((property, oldImg, newImg) -> {
      logger.info("mapImageView modified");
      contentPane.setPrefWidth(newImg.getWidth());
      contentPane.setPrefHeight(newImg.getHeight());

      foregroundPane.setPrefWidth(newImg.getWidth());
      foregroundPane.setPrefHeight(newImg.getHeight());

      mapImageView.setFitWidth(newImg.getWidth());
      mapImageView.setFitHeight(newImg.getHeight());
    });
  }

  public Pane getForegroundPane() {
    return foregroundPane;
  }
}
