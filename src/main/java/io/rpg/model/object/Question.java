package io.rpg.model.object;

import com.google.gson.annotations.SerializedName;

public class Question {
  @SerializedName(value = "header", alternate = {"question"})
  private String question;

  @SerializedName(value = "a", alternate = {"A"})
  private String answerA;

  @SerializedName(value = "b", alternate = {"B"})
  private String answerB;

  @SerializedName(value = "c", alternate = {"C"})
  private String answerC;

  @SerializedName(value = "d", alternate = {"D"})
  private String answerD;

  @SerializedName("correct")
  private char correctAnswerChar;

  public Question(String question, String[] answers, char correctAnswerChar) {
    this.question = question;
    this.answerA = answers[0];
    this.answerB = answers[1];
    this.answerC = answers[2];
    this.answerD = answers[3];
    this.correctAnswerChar = correctAnswerChar;
  }

  public String getQuestion() {
    return question;
  }

  public String getAnswerA() {
    return answerA;
  }

  public String getAnswerB() {
    return answerB;
  }

  public String getAnswerC() {
    return answerC;
  }

  public String getAnswerD() {
    return answerD;
  }

  public String getCorrectAnswer() {
    return switch (Character.toUpperCase(correctAnswerChar)) {
      case 'A' -> this.answerA;
      case 'B' -> this.answerB;
      case 'C' -> this.answerC;
      case 'D' -> this.answerD;
      default -> throw new IllegalStateException("Invalid correct answer specification");
    };
  }

  public char getCorrectAnswerChar() {
    return Character.toUpperCase(correctAnswerChar);
  }
}
