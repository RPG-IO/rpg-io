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
    viewModel.setQuestion(question.question(), question.answers());

    viewModel.setButtonCallback('A', event -> this.answerSelected('A'));
    viewModel.setButtonCallback('B', event -> this.answerSelected('B'));
    viewModel.setButtonCallback('C', event -> this.answerSelected('C'));
    viewModel.setButtonCallback('D', event -> this.answerSelected('D'));

    this.setFill(Color.TRANSPARENT);
  }

  public void answerSelected(char answer) {
    char correctAnswer = question.correctAnswer();
    if (answer == correctAnswer){
      System.out.println("Correct!");
    } else {
      System.out.println("Answer " + answer + " is incorrect. The correct answer is " + correctAnswer + ": " + question.answers()[getAnswerIndex(correctAnswer)]);
    }
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
