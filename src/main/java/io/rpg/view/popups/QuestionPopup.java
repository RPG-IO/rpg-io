package io.rpg.view.popups;

import io.rpg.viewmodel.QuestionPopupViewModel;
import io.rpg.viewmodel.TextPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class QuestionPopup extends Scene {

  private final QuestionPopupViewModel viewModel;
  private final char correctAnswer;

  public QuestionPopup(String question, String [] answers, char correctAnswer, String backgroundPath) {
    this(question, answers, correctAnswer);
    viewModel.setBackgroundImage(backgroundPath);
  }

  public QuestionPopup(String question, String [] answers, char correctAnswer) {
    super(new Group(), Color.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(QuestionPopupViewModel.class.getResource("question-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setQuestion(question, answers);

    this.correctAnswer = correctAnswer;
    this.setFill(Color.TRANSPARENT);
  }
}
