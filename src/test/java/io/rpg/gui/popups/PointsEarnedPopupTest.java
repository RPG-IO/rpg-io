package io.rpg.gui.popups;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class PointsEarnedPopupTest extends ApplicationTest {

    @Start
    public void start(Stage stage) {
        PointsEarnedPopup pointsPopup = new PointsEarnedPopup();
        Scene scene = new Scene(new Group());
        stage.setScene(scene);

        Stage popupStage = pointsPopup.getPopup(3, stage.getScene());
        stage.setScene(popupStage.getScene());
        stage.show();
    }

    @Test
    public void checkCorrectLabel(FxRobot robot) {
        Assertions.assertThat(robot.lookup(".label").queryAs(Label.class)).hasText("Earned 3 points!");
    }
}