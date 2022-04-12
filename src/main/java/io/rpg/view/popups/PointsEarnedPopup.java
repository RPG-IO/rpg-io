package io.rpg.view.popups;

import io.rpg.viewmodel.PointsPopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Objects;

public class PointsEarnedPopup {

    private final FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(PointsEarnedPopup.class.getResource("points-earned-view.fxml")));;
    private Parent root;
    private PointsPopupController controller;
    private final Scene popupScene;

    public PointsEarnedPopup(){
        // read FXML view
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        popupScene = new Scene(root, Color.TRANSPARENT);
    }

    public Stage getPopup(int pointsCount, Scene scene) {
        // fill dynamic view components
        if (controller == null) controller = loader.getController();
        controller.setPointsCount(pointsCount);
        Pair<Double, Double> backgroundDims = controller.setBackgroundImage("file:assets/point-popup-bg.png");

        // create popup stage
        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        Window window = scene.getWindow();
        popupStage.initOwner(window);

        // add and center popupScene on popup stage
        popupStage.setScene(popupScene);
        popupStage.setX(window.getX() + window.getWidth()/2 - backgroundDims.getKey()/2);
        popupStage.setY(window.getY() + window.getHeight()/2 - backgroundDims.getValue()/2);

        // close popup after clicking aside
        popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popupStage.close();
            }
        });

        return popupStage;
    }
}
