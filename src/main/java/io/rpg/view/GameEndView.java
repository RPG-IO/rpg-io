package io.rpg.view;

import io.rpg.viewmodel.GameEndViewModel;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameEndView extends Scene {
  private GameEndViewModel viewModel;

  private GameEndView(Parent parent) {
    super(parent);
  }

  public static GameEndView load() {
    GameEndViewModel viewModel;
    try {
      viewModel = GameEndViewModel.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    GameEndView view = new GameEndView(viewModel.getParent());
    view.viewModel = viewModel;
    return view;
  }

  public void setDescription(String description) {
    viewModel.setDescription(description);
  }
}
