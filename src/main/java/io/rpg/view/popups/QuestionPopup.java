package io.rpg.view.popups;

import io.rpg.model.object.Question;
import io.rpg.viewmodel.QuestionPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class QuestionPopup extends Scene {

  private final QuestionPopupViewModel viewModel;
  private final Question question;
  private EventHandler<? super MouseEvent> successCallback;
  private EventHandler<? super MouseEvent> failureCallback;

  public QuestionPopup(Question question, String backgroundPath) {
    this(question);
    viewModel.setBackgroundImage(backgroundPath);
  }

  public QuestionPopup(Question question) {
    super(new Group(), Color.TRANSPARENT);

    this.question = question;

    FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(QuestionPopupViewModel.class.getResource("question-popup-view.fxml")));
    Parent root = null;

    try {
      root = loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setRoot(root);

    viewModel = loader.getController();
    viewModel.setQuestion(question);

    viewModel.setButtonCallback('A', event -> this.answerSelected('A'));
    viewModel.setButtonCallback('B', event -> this.answerSelected('B'));
    viewModel.setButtonCallback('C', event -> this.answerSelected('C'));
    viewModel.setButtonCallback('D', event -> this.answerSelected('D'));

    viewModel.setBackgroundImage("file:assets/popup-background-3.png");

    this.setFill(Color.TRANSPARENT);
  }

  public void answerSelected(char answer) {
    char correctAnswer = question.getCorrectAnswerChar();
    if (answer == correctAnswer){
      viewModel.setQuestionLabel("Correct!");
      this.setOnMouseClicked(this.successCallback);
    } else {
      viewModel.highlightWrong(answer);
      viewModel.setQuestionLabel("Answer " + answer + " is incorrect. The correct answer is " + correctAnswer + ": " + question.getCorrectAnswer());
      this.setOnMouseClicked(this.failureCallback);
    }

    viewModel.highlightCorrect(correctAnswer);
    viewModel.removeButtonCallbacks();
  }

  public void setSuccessCallback(EventHandler<? super MouseEvent> successCallback) {
    this.successCallback = successCallback;
  }

  public void setFailureCallback(EventHandler<? super MouseEvent> failureCallback) {
    this.failureCallback = failureCallback;
  }
}
