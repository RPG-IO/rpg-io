package io.rpg.model.actions;

import io.rpg.model.actions.condition.Condition;
import io.rpg.model.object.Question;

/**
 * Class for storing local data needed to preform a quiz action.
 */

public class QuizAction extends BaseAction {

  public final Question question;
  private int pointsToEarn;

  public QuizAction(Question question, Condition condition) {
    super(condition);
    this.question = question;
    this.pointsToEarn = 10;
  }

  public void setPointsToEarn(int pointsToEarn) {
    this.pointsToEarn = pointsToEarn;
  }

  public int getPointsToEarn() {
    return pointsToEarn;
  }

  @Override
  public void acceptActionEngine(ActionEngine engine) {
    engine.onAction(this);
  }
}
