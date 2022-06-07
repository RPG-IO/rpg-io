package io.rpg.view.popups;

import io.rpg.view.GameObjectView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

public class BattleReflexPopup extends Scene {

  private final int SPACING = 25;
  private final char[] singleMove = {'W', 'A', 'S', 'D'};
  private final Random random;
  private final String sequence;
  private final int sequenceLength = 5;
  private final Label[] characterLabels;
  private final ImageView[] timerDots;

  private final BiConsumer<Boolean, Integer> callback;
  private final int pointsPerSecond;
  private int currentSequencePosition;
  private final Timer timer;
  private int timeToCountDown;
  private final Button button;

  private static String backgroundPath;
  private static String buttonPath;


  public BattleReflexPopup(int pointsPerSecond, BiConsumer<Boolean, Integer> callback) {
    super(new Group(), Color.TRANSPARENT);

    this.callback = callback;
    this.pointsPerSecond = pointsPerSecond;

    this.timer = new Timer();
    this.timeToCountDown = 5;

    this.random = new Random();
    this.timerDots = new ImageView[timeToCountDown];
    this.sequence = generateRandomSequence(sequenceLength);
    this.button = new Button("OK");

    Group group = new Group();
    ImageView background = new ImageView(backgroundPath);

    ImageView swordIcon = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/sword6.png"));
    background.setX(0);
    background.setY(0);

    this.characterLabels = new Label[sequenceLength];
    double centerX = getX() + getWidth() / 2;
    double centerY = getY() + getHeight() / 2;
    int singleLabelWidth = 25;
    double allLabelsWidth = sequenceLength * singleLabelWidth + (sequenceLength - 1) * SPACING;
    this.currentSequencePosition = 0;

    ImageView imageViewButton = new ImageView(buttonPath);
    button.setLayoutX(background.getImage().getWidth() / 2 - imageViewButton.getImage().getWidth() / 2);
    button.setLayoutY(background.getImage().getHeight() - imageViewButton.getImage().getHeight() / 2);
    button.setGraphic(imageViewButton);
    button.setContentDisplay(ContentDisplay.CENTER);
    button.setStyle("-fx-background-color: transparent;");
    group.getChildren().add(background);
    group.getChildren().add(button);

    for (int i = 0; i < 5; i++) {
      timerDots[i] = new ImageView(GameObjectView.resolvePathToJFXFormat("assets/button-image.png"));
      timerDots[i].setLayoutX(i * SPACING + i * singleLabelWidth + background.getImage().getWidth() / 2 - allLabelsWidth / 2);
      timerDots[i].setLayoutY(centerY + background.getImage().getHeight() / 2 + 50);
      timerDots[i].setScaleX(0.5);
      timerDots[i].setScaleY(0.5);
      group.getChildren().add(timerDots[i]);
    }

    group.getChildren().add(swordIcon);

    for (int i = 0; i < sequenceLength; i++) {
      this.characterLabels[i] = new Label(String.valueOf(sequence.charAt(i)));
      this.characterLabels[i].setStyle("-fx-font-family: Monospaced; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: " + 18);
      this.characterLabels[i].setLayoutX(i * SPACING + i * singleLabelWidth + background.getImage().getWidth() / 2 - allLabelsWidth / 2);
      this.characterLabels[i].setLayoutY(centerY + background.getImage().getHeight() / 2);
      swordIcon.setLayoutX(background.getImage().getWidth() / 2);
      swordIcon.setLayoutY(20);
      group.getChildren().add(characterLabels[i]);
      double x = characterLabels[i].getLayoutX();
      double y = characterLabels[i].getLayoutY();
      System.out.println(x + " " + y);
    }

    this.setRoot(group);
    this.setFill(Color.TRANSPARENT);

    setOnKeyPressed((event) -> {
      String str = event.getCode().getChar();
      if (currentSequencePosition < sequenceLength) {
        if (str.equals(String.valueOf(sequence.charAt(currentSequencePosition)))) {
          this.characterLabels[currentSequencePosition].setStyle("-fx-font-family: Monospaced; -fx-text-fill: #2bee1e; -fx-font-weight: bold; -fx-font-size: " + 18);
          currentSequencePosition++;
          if (this.currentSequencePosition == sequenceLength) {
            win();
          }
        } else {
          this.characterLabels[currentSequencePosition].setStyle("-fx-font-family: Monospaced; -fx-text-fill: #fa2902; -fx-font-weight: bold; -fx-font-size: " + 18);
          currentSequencePosition++;
        }
      }
    });

    timer.schedule(new TimerTask() {
      public void run() {
        Platform.runLater(() -> timerTick());
      }
    }, 1000, 1000);
    button.setOnAction((event) -> {
    });
  }

  public static void setBackgroundPath(String backgroundPath) {
    BattleReflexPopup.backgroundPath = backgroundPath;
  }

  public static void setButtonPath(String buttonPath) {
    BattleReflexPopup.buttonPath = buttonPath;
  }

  public String generateRandomSequence(int length) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < length; i++) {
      str.append(singleMove[random.nextInt(singleMove.length)]);
    }
    return str.toString();
  }

  private void timerTick() {
    timeToCountDown--;
    if (timeToCountDown < 0) {
      timer.cancel();
      lose();
      return;
    }
    timerDots[timeToCountDown].setImage(new Image(GameObjectView.resolvePathToJFXFormat("assets/button-image-2.png")));
  }

  public void win() {
    callback.accept(true, timeToCountDown * pointsPerSecond);
    timer.cancel();
  }

  public void lose() {
    callback.accept(false, 0);
  }

  public void setCloseButtonActionListener(EventHandler<ActionEvent> value) {
    button.setOnAction(value);
  }
}
