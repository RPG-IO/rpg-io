package io.rpg.viewmodel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameObjectViewModel implements Initializable {
    @FXML
    private Pane imgPane;

    @FXML
    private ImageView gameObjectView;
    @FXML
    private HBox parent;

    public GameObjectViewModel() {
    }

    public void setImage(Image image){
        this.gameObjectView.imageProperty().setValue(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameObjectView.imageProperty().addListener((property, oldImg, newImg) -> {
            imgPane.setPrefWidth(newImg.getWidth());
            imgPane.setPrefHeight(newImg.getHeight());
        });
    }
}
