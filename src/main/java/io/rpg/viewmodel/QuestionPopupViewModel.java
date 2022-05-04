package io.rpg.viewmodel;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class QuestionPopupViewModel {

  @FXML private Label questionLabel;
  @FXML private Pane backgroundPane;
  @FXML private ImageView backgroundImage;
  @FXML private Button aButton, bButton, cButton, dButton;

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

  private Button getButtonFromCode(char buttonCode) {
    return switch (buttonCode) {
      case 'A' -> aButton;
      case 'B' -> bButton;
      case 'C' -> cButton;
      case 'D' -> dButton;
      default -> throw new IllegalStateException("Unexpected value: " + buttonCode);
    };
  }

  public void setButtonCallback(char buttonCode, EventHandler<? super MouseEvent> callback) {
    getButtonFromCode(buttonCode).setOnMouseClicked(callback);
  }

  public void highlightCorrect(char buttonCode) {
    Button button = getButtonFromCode(buttonCode);
    button.setStyle(button.getStyle() + "-fx-border-color: #3b803b; -fx-border-width: 5px;");
  }

  public void highlightWrong(char buttonCode) {
    Button button = getButtonFromCode(buttonCode);
    button.setStyle(button.getStyle() + "-fx-border-color: #a93e3e; -fx-border-width: 5px;");
  }
}
