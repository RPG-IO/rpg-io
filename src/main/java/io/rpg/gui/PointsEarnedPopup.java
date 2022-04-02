package io.rpg.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class PointsEarnedPopup {


    public static void showPopup(int pointsCount, Scene scene){
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(PointsEarnedPopup.class.getResource("points-earned-view.fxml")));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(scene.getWindow());
        popupStage.initModality(Modality.APPLICATION_MODAL);

        popupStage.setScene(new Scene(root, Color.TRANSPARENT));

        PointsPopupController controller = loader.getController();
        controller.setPointsCount(pointsCount);

        popupStage.show();

        popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popupStage.close();
            }
        });
    }
}
