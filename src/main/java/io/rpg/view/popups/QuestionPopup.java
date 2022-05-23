package io.rpg.view.popups;

import io.rpg.model.object.Question;
import io.rpg.viewmodel.QuestionPopupViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class QuestionPopup extends Scene {

  private final QuestionPopupViewModel viewModel;
  private final Question question;
  private Runnable successCallback;
  private Runnable failureCallback;

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
      viewModel.setAllButtonsCallback(event -> this.successCallback.run());
      this.setOnMouseClicked(event -> this.successCallback.run());
    } else {
      viewModel.highlightWrong(answer);
      viewModel.setQuestionLabel("Answer " + answer + " is incorrect. The correct answer is " + correctAnswer + ": " + question.getCorrectAnswer());
      viewModel.setAllButtonsCallback(event -> this.failureCallback.run());
      this.setOnMouseClicked(event -> this.failureCallback.run());
    }

    viewModel.highlightCorrect(correctAnswer);
  }

  public void setSuccessCallback(Runnable successCallback) {
    this.successCallback = successCallback;
  }

  public void setFailureCallback(Runnable failureCallback) {
    this.failureCallback = failureCallback;
  }
}
