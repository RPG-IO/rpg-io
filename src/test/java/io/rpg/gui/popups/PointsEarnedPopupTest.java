package io.rpg.gui.popups;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;


public class PointsEarnedPopupTest {

    private final JFXPanel panel = new JFXPanel();
    private final PointsEarnedPopup pointsPopup = new PointsEarnedPopup();

    @Test
    public void checkCloseOnNoFocus() {
        Platform.runLater(() -> {
            Stage mainStage = new Stage(StageStyle.TRANSPARENT);
            mainStage.initOwner(null);
            Scene mainScene = new Scene(new Group());
            mainStage.setScene(mainScene);
            Stage popupStage = pointsPopup.getPopup(5, mainScene);

            popupStage.show();
            mainStage.requestFocus();

            assertFalse(popupStage.isShowing());
        });
    }
}
