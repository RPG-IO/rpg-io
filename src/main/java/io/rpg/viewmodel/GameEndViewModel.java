package io.rpg.viewmodel;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class GameEndViewModel {
  public static final String GAME_END_VIEW_FXML = "game-end-view.fxml";
  private Parent parent;
  @FXML  private Label descriptionLabel;

  public static GameEndViewModel load() throws IOException {
    URL url = Objects.requireNonNull(GameEndViewModel.class.getResource(GAME_END_VIEW_FXML));
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    GameEndViewModel viewModel = loader.getController();
    viewModel.parent = root;
    return viewModel;
  }

  public Parent getParent() {
    return parent;
  }

  public void setDescription(String description) {
    descriptionLabel.setText(description);
  }
}
