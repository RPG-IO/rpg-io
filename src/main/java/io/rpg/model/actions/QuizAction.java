package io.rpg.model.actions;

import io.rpg.model.object.Question;

/**
 * Class for storing local data needed to preform a quiz action.
 */

public class QuizAction implements Action {

  public final Question question;
  public final int pointsToEarn;

  public QuizAction(Question question) {
    this.question = question;
    this.pointsToEarn = 10;
  }
}
