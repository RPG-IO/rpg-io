package io.rpg.model.actions.condition;

import io.rpg.model.actions.ConditionType;
import org.jetbrains.annotations.NotNull;

public final class DefeatOpponentCondition extends Condition {

  @NotNull
  private final String opponentTag;

  public DefeatOpponentCondition(@NotNull final String tag) {
    super(ConditionType.DEFEAT_OPPONENT);
    this.opponentTag = tag;
  }

  @Override
  public boolean acceptEngine(ConditionEngine engine) {
    return engine.evaluateDefeatOpponentCondition(this);
  }

  @NotNull
  public String getOpponentTag() {
    return opponentTag;
  }
}
