package io.rpg.gui;

import io.rpg.gui.model.LocationModel;
import io.rpg.gui.popups.PointsEarnedPopup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocationController implements Initializable {
    private final static URL FXML_URL = LocationController.class.getResource("location-view.fxml");

    @FXML private ImageView mapImageView;
    @FXML private Pane foregroundPane, contentPane;
    @FXML private HBox parent;

    private LocationModel model;
    private Scene scene;

    private final PointsEarnedPopup pointsPopup = new PointsEarnedPopup();

    public static LocationController load() throws IOException {
        FXMLLoader loader = new FXMLLoader(FXML_URL);
        loader.load();
        return loader.getController();
    }

    public LocationController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapImageView.imageProperty().addListener((property, oldImg, newImg) -> {
            contentPane.setPrefWidth(newImg.getWidth());
            contentPane.setPrefHeight(newImg.getHeight());

            foregroundPane.setPrefWidth(newImg.getWidth());
            foregroundPane.setPrefHeight(newImg.getHeight());

            mapImageView.setFitWidth(newImg.getWidth());
            mapImageView.setFitHeight(newImg.getHeight());

        });

        model = new LocationModel(mapImageView.imageProperty(), foregroundPane.getChildren(), this);

        scene = new Scene(parent);
        scene.addEventFilter(KeyEvent.KEY_TYPED, this::onKeyTyped);
    }

    public void onKeyTyped(KeyEvent event) {
        // TODO: 01.04.2022 Implement key actions

        System.out.println(event);

        pointsPopup.showPopup(5, scene);
    }

    public LocationModel getModel(){
        return model;
    }

    public Scene getScene() {
        return scene;
    }
}
