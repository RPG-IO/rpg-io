package io.rpg.viewmodel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class QuestionPopupViewModel {

  @FXML private Label questionLabel;
  @FXML private Pane backgroundPane;
  @FXML private ImageView backgroundImage;
  @FXML private Button aButton, bButton, cButton, dButton;

  public void setQuestion(String question, String[] answers) {
    questionLabel.setText(question);
    aButton.setText(answers[0]);
    bButton.setText(answers[1]);
    cButton.setText(answers[2]);
    dButton.setText(answers[3]);
    System.out.println("set");
  }

  public void setTextSize(int size) {
    questionLabel.setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + size);
  }

  public void setBackgroundImage(String url) {
    Image image = new Image(url);
    backgroundImage.setImage(image);
  }
}
