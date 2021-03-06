package io.rpg.model.actions;

import com.google.gson.annotations.SerializedName;

public enum ConditionType {
  @SerializedName("item-required")
  ITEM_REQUIRED,

  @SerializedName("defeat-opponent")
  DEFEAT_OPPONENT,

  @SerializedName("level-required")
  LEVEL_REQUIRED,

  @SerializedName("points-required")
  POINTS_REQUIRED,
}
