package io.rpg.gui.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Pair;

public class PointsPopupController {

    @FXML private Label label;
    @FXML private Pane background;

    protected void setPointsCount(int pointsCount) {
        label.setText("Earned " + pointsCount + " points!");
    }

    protected Pair<Double, Double> setBackgroundImage(String url){
        BackgroundImage backgroundImg= new BackgroundImage(
                new Image(url),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        background.setBackground(new Background(backgroundImg));
        return new Pair<>(background.getPrefWidth(), background.getPrefHeight());
    }
}