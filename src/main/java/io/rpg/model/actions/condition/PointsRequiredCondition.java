package io.rpg.model.actions.condition;

import io.rpg.model.actions.ConditionType;

public class PointsRequiredCondition extends Condition {

  private final int points;

  public PointsRequiredCondition(int points) {
    super(ConditionType.POINTS_REQUIRED);
    this.points = points;
  }

  @Override
  public boolean acceptEngine(ConditionEngine engine) {
    return engine.evaluate(this);
  }

  public int getPoints() {
    return points;
  }
}
