package io.rpg.viewmodel;

import io.rpg.model.data.MouseClickedEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Set;

public class QuestionPopupViewModel {

  @FXML private Label questionLabel;
  @FXML private Pane backgroundPane;
  @FXML private ImageView backgroundImage;
  @FXML private Button aButton, bButton, cButton, dButton;
  private final Set<Scene> onClickedObservers = new HashSet<>();


  public void setQuestion(String question, String[] answers) {
    questionLabel.setText(question);
    aButton.setText("A: " + answers[0]);
    bButton.setText("B: " + answers[1]);
    cButton.setText("C: " + answers[2]);
    dButton.setText("D: " + answers[3]);
  }

  public void setTextSize(int size) {
    questionLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + size);
  }

  public void setBackgroundImage(String url) {
    Image image = new Image(url);
    backgroundImage.setImage(image);
  }

  public void setButtonCallback(char buttonCode, EventHandler<? super MouseEvent> callback) {
    switch (buttonCode) {
      case 'A'-> aButton.setOnMouseClicked(callback);
      case 'B'-> bButton.setOnMouseClicked(callback);
      case 'C'-> cButton.setOnMouseClicked(callback);
      case 'D'-> dButton.setOnMouseClicked(callback);
    }
  }
}
