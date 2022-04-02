package io.rpg.gui.popups;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Pair;

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
        Window window = scene.getWindow();
        popupStage.initOwner(window);
//        popupStage.initModality(Modality.APPLICATION_MODAL);

        PointsPopupController controller = loader.getController();
        controller.setPointsCount(pointsCount);
        Pair<Double, Double> backgroundDims = controller.setBackgroundImage("file:assets/point-popup-bg.png");

        popupStage.setScene(new Scene(root, Color.TRANSPARENT));


        popupStage.setX(window.getX() + window.getWidth()/2 - backgroundDims.getKey()/2);
        popupStage.setY(window.getY() + window.getHeight()/2 - backgroundDims.getValue()/2);
        popupStage.show();

        popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                popupStage.close();
            }
        });
    }
}
