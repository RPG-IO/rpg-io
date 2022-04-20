package io.rpg.view.popups;

import io.rpg.viewmodel.PointsPopupViewModel;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class PointsEarnedPopup extends Scene {

  private final PointsPopupViewModel controller;

  public PointsEarnedPopup(String backgroundPath) throws IOException {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(PointsPopupViewModel.class.getResource("points-earned-view.fxml")));
    Parent root = loader.load();
    this.setRoot(root);

    controller = loader.getController();
    controller.setBackgroundImage(backgroundPath);
  }

  public PointsEarnedPopup() throws IOException {
    this("file:assets/point-popup-bg.png");
  }

  public void setPointsCount(int pointsCount){
    controller.setPointsCount(pointsCount);
  }
}
