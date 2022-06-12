package io.rpg.view.popups;

import io.rpg.model.object.Question;
import io.rpg.viewmodel.QuestionPopupViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;

public class QuestionPopup extends Scene {

  private final QuestionPopupViewModel viewModel;
  private final Question question;
  private final int pointsToEarn;
  private final BiConsumer<Boolean, Integer> callback;

  private static String backgroundPath;
  private static String buttonPath;

  public QuestionPopup(Question question, BiConsumer<Boolean, Integer> callback, int pointsToEarn) {
    super(new Group(), Color.TRANSPARENT);

    this.question = question;
    this.pointsToEarn = pointsToEarn;
    this.callback = callback;

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

    viewModel.setBackgroundImage(backgroundPath);
    viewModel.setOkButtonImage(buttonPath);

    this.setFill(Color.TRANSPARENT);
  }

  public static void setBackgroundPath(String backgroundPath) {
    QuestionPopup.backgroundPath = backgroundPath;
  }

  public static void setButtonPath(String buttonPath) {
    QuestionPopup.buttonPath = buttonPath;
  }


  public void answerSelected(char answer) {
    char correctAnswer = question.getCorrectAnswerChar();
    this.viewModel.setAllButtonsCallback(null);
    if (answer == correctAnswer) {
      viewModel.setQuestionLabel("Poprawna odpowiedź!");
      this.setOkButtonCallback(event -> callback.accept(true, pointsToEarn));
    } else {
      viewModel.highlightWrong(answer);
      viewModel.setQuestionLabel("Poprawna odpowiedź: " + question.getCorrectAnswerChar());
      this.setOkButtonCallback(event -> callback.accept(false, 0));
    }

    viewModel.highlightCorrect(correctAnswer);
  }

  public void setOkButtonCallback(EventHandler<? super MouseEvent> callback) {
    viewModel.setOkButtonOnClick(callback);
  }
}
