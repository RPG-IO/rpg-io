package io.rpg.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class TextPopupViewModel {

  @FXML private Label label;
  @FXML private Pane backgroundPane;
  @FXML private ImageView backgroundImage;
  @FXML private Button okButton;

  public void setText(String text) {
    label.setText(text);
  }

  public void setBackgroundImage(String url) {
    Image image = new Image(url);
    backgroundImage.setImage(image);
  }

  public void setOkButtonImage(String url) {
    ImageView imageView = new ImageView(url);
    okButton.setGraphic(imageView);
  }

  public void setButtonOnClick(javafx.event.EventHandler<? super javafx.scene.input.MouseEvent> callback) {
    okButton.setOnMouseClicked(callback);
  }
}