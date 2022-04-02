package io.rpg.gui.popups;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PointsPopupController {

    @FXML
    private Label label;

    protected void setPointsCount(int pointsCount) {
        label.setText("Earned " + pointsCount + " points");
    }
}