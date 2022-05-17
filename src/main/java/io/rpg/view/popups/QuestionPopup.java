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

  private static String backgroundPath;

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
    viewModel.setQuestion(question.question(), question.answers());

    viewModel.setButtonCallback('A', event -> this.answerSelected('A'));
    viewModel.setButtonCallback('B', event -> this.answerSelected('B'));
    viewModel.setButtonCallback('C', event -> this.answerSelected('C'));
    viewModel.setButtonCallback('D', event -> this.answerSelected('D'));

    viewModel.setBackgroundImage(backgroundPath);

    this.setFill(Color.TRANSPARENT);
  }

  public static void setBackgroundPath(String backgroundPath) {
    QuestionPopup.backgroundPath = backgroundPath;
  }

  public void answerSelected(char answer) {
    char correctAnswer = question.correctAnswer();
    if (answer == correctAnswer){
      viewModel.setQuestionLabel("Correct!");
    } else {
      viewModel.highlightWrong(answer);
      viewModel.setQuestionLabel("Answer " + answer + " is incorrect. The correct answer is " + correctAnswer + ": " + question.answers()[getAnswerIndex(correctAnswer)]);
    }

    viewModel.highlightCorrect(correctAnswer);
    viewModel.removeButtonCallbacks();
  }

  private int getAnswerIndex(char answerCode) {
    return switch (answerCode) {
      case 'A' -> 0;
      case 'B' -> 1;
      case 'C' -> 2;
      case 'D' -> 3;
      default -> throw new IllegalStateException("Unexpected answer code: " + answerCode);
    };
  }
}
