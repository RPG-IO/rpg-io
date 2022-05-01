package io.rpg.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TextImagePopupViewModel {

  @FXML private Label label;
  @FXML private Pane backgroundPane;
  @FXML private ImageView backgroundImage;
  @FXML private Button okButton;
  @FXML private ImageView imageView;


  public void setText(String text) {
    if (text.length() < 40) setTextSize(25);
    else if (text.length() < 120) setTextSize(19);
    else setTextSize(13);
    label.setText(text);
  }

  public void setImage(Image image) {
    imageView.setImage(image);
  }

  public void setTextSize(int size) {
    System.out.println(size);
    label.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + size);
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
