package io.rpg.viewmodel;

import io.rpg.model.object.Question;
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
  @FXML private Button okButton;

  public void setQuestion(Question question) {
    questionLabel.setText(question.getQuestion());
    aButton.setText("A: " + question.getAnswerA());
    bButton.setText("B: " + question.getAnswerB());
    cButton.setText("C: " + question.getAnswerC());
    dButton.setText("D: " + question.getAnswerD());
  }

  public void setQuestionLabel(String text) {
    questionLabel.setText(text);
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

  public void setOkButtonOnClick(EventHandler<? super MouseEvent> callback) {
    okButton.setOnMouseClicked(callback);
  }

  public void setAllButtonsCallback(EventHandler<? super MouseEvent> callback) {
    aButton.setOnMouseClicked(callback);
    bButton.setOnMouseClicked(callback);
    cButton.setOnMouseClicked(callback);
    dButton.setOnMouseClicked(callback);
  }

  public void highlightCorrect(char buttonCode) {
    Button button = getButtonFromCode(buttonCode);
    button.setStyle(button.getStyle() + "-fx-border-color: #3b803b; -fx-border-width: 5px;");
  }

  public void highlightWrong(char buttonCode) {
    Button button = getButtonFromCode(buttonCode);
    button.setStyle(button.getStyle() + "-fx-border-color: #a93e3e; -fx-border-width: 5px;");
  }

  public void setOkButtonImage(String url) {
    ImageView imageView = new ImageView(url);
    okButton.setGraphic(imageView);
  }
}
