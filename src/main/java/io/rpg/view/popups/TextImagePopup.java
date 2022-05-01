package io.rpg.view.popups;

import io.rpg.viewmodel.TextImagePopupViewModel;
import io.rpg.viewmodel.TextPopupViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class TextImagePopup extends Scene {

  private final TextImagePopupViewModel viewModel;

  public TextImagePopup(String text, Image image, String backgroundPath, String buttonPath) {
    this(text, image);
    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setOkButtonImage(buttonPath);
  }

  public TextImagePopup(String text, Image image) {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(TextPopupViewModel.class.getResource("text-image-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setText(text);
    viewModel.setImage(image);
    this.setFill(Color.TRANSPARENT);
  }

  public void setButtonCallback(javafx.event.EventHandler<? super javafx.scene.input.MouseEvent> callback){
    this.viewModel.setButtonOnClick(callback);
  }
}
