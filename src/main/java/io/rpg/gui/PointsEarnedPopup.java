package io.rpg.gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PointsEarnedPopup {
    public static void showPopup(int pointsCount, Scene scene){
        VBox pauseRoot = new VBox(5);
        pauseRoot.getChildren().add(new Label("Earned " + pointsCount + " points"));

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(scene.getWindow());
        popupStage.initModality(Modality.APPLICATION_MODAL);

        popupStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));

        popupStage.show();

        popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popupStage.close();
            }
        });
    }
}
