package io.rpg.viewmodel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class DialoguePopupViewModel {

  @FXML private Pane backgroundPane;
  @FXML private StackPane npcFramePane;
  @FXML private ImageView backgroundImage;
  @FXML private ImageView npcImage;
  @FXML private ImageView npcFrameImage;
  @FXML private Text text;
  @FXML private Button closeButton;
  @FXML private Button nextButton;
  @FXML private Button previousButton;

  public void setText(String text) {
    this.text.setText(text);
  }

  public void setNpcImage(Image image) {
    npcImage.setImage(image);
  }

  public void setBackgroundImage(String url) {
    Image image = new Image(url);
    backgroundImage.setImage(image);
  }

  public void setCloseButtonImage(String url) {
    ImageView imageView = new ImageView(url);
    imageView.setFitWidth(16);
    imageView.setPreserveRatio(true);
    closeButton.setGraphic(imageView);
  }

  public void setNextButtonImage(String url) {
    ImageView imageView = new ImageView(url);
    imageView.setFitWidth(26);
    imageView.setPreserveRatio(true);
    nextButton.setGraphic(imageView);
  }

  public void setPreviousButtonImage(String url) {
    ImageView imageView = new ImageView(url);
    imageView.setFitWidth(26);
    imageView.setPreserveRatio(true);
    previousButton.setGraphic(imageView);
  }

  public void setNpcFrameImage(String url) {
    Image image = new Image(url);
    npcFrameImage.setImage(image);
  }

  public void setCloseButtonOnClick(EventHandler<? super MouseEvent> callback) {
    closeButton.setOnMouseClicked(callback);
  }

  public void setNextVisibility(boolean value) {
    nextButton.setVisible(value);
  }

  public void setPreviousVisibility(boolean value) {
    previousButton.setVisible(value);
  }

  public void setNextButtonOnClick(EventHandler<? super MouseEvent> callback) {
    nextButton.setOnMouseClicked(callback);
  }

  public void setPreviousButtonOnClick(EventHandler<? super MouseEvent> callback) {
    previousButton.setOnMouseClicked(callback);
  }
}
