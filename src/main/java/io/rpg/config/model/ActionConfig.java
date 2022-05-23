package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import io.rpg.model.object.Question;
import io.rpg.util.ErrorMessageBuilder;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents action configuration.
 */
public class ActionConfig {
  /**
   * Unique tag, representing the action.
   *
   * Nullable because this class is instantiated via Gson
   * from user config. However, it is granted, that after
   * successful validation this field is not null and is not
   * blank string.
   */
  @Nullable
  private String tag;

  /**
   * String representing action type.
   * Must match strings defined in enum
   * Action type enum.
   *
   * Nullable because this class is instantiated via Gson
   * from user config. However, it is granted, that after
   * successful validation this field is not null and is not
   * blank string.
   */
  @Nullable
  @SerializedName("type")
  private String actionTypeString;

  /**
   * Action to be triggered before the proper action is executed.
   */
  @Nullable
  @SerializedName(value = "before", alternate = {"beforeAction", "before-action"})
  private ActionConfig beforeAction;

  /**
   * Action to be triggered after the proper action had been executed.
   */
  @Nullable
  @SerializedName(value = "after", alternate = {"afterAction", "after-action"})
  private ActionConfig afterAction;

  /**
   * {@link io.rpg.model.actions.DialogueAction} <br>
   * TODO
   */
  @Nullable
  @SerializedName(value = "statements", alternate = {"text", "dialogueStatements", "dialogue-statements"})
  private List<String> dialogueStatements;

  /**
   * {@link io.rpg.model.actions.DialogueAction} <br>
   * {@link io.rpg.model.actions.ShowDescriptionAction} <br>
   * TODO
   */
  @Nullable
  @SerializedName(value = "assetPath", alternate = {"asset", "asset-path"})
  private String assetPath;

  /**
   * {@link io.rpg.model.actions.GameEndAction} <br>
   * {@link io.rpg.model.actions.ShowDescriptionAction} <br>
   * TODO
   */
  @Nullable
  private String description;

  /**
   * {@link io.rpg.model.actions.LocationChangeAction} <br>
   * TODO
   */
  @Nullable
  @SerializedName(value = "targetLocation", alternate = {"target-location", "target"})
  private String targetLocationTag;

  /**
   * {@link io.rpg.model.actions.QuizAction} <br>
   * TODO
   */
  @Nullable
  // TODO: Create QuestionConfig model class
  private List<Question> questions;


  @Nullable
  @SerializedName(value = "reward", alternate = {"reward-points", "rewardPoints"})
  // TODO: consider introducing reward type & then we can parse the reward string
  // accordingly
  private Integer rewardPoints;

  /**
   * Package scoped constructor, meant for test purposes only.
   */
  ActionConfig(@NotNull String tag, @NotNull String actionType) {
    this.tag = tag;
    this.actionTypeString = actionType;
  }

  /**
   * @return Unique tag, representing the action.
   *
   * Nullable because this class is instantiated via Gson
   * from user config. However, it is granted, that after
   * successful validation this field is not null and is not
   * blank string.
   */
  @Nullable
  public String getTag() {
    return tag;
  }

  /**
   * @return String representing action type.
   * Must match strings defined in enum
   * Action type enum.
   *
   * Nullable because this class is instantiated via Gson
   * from user config. However, it is granted, that after
   * successful validation this field is not null and is not
   * blank string.
   */
  @Nullable
  public String getActionTypeString() {
    return actionTypeString;
  }

  @Nullable
  public ActionConfig getBeforeAction() {
    return beforeAction;
  }

  @Nullable
  public ActionConfig getAfterAction() {
    return afterAction;
  }

  @Nullable
  public List<String> getDialogueStatements() {
    return dialogueStatements;
  }

  @Nullable
  public String getDescription() {
    return description;
  }

  @Nullable
  public String getAssetPath() {
    return assetPath;
  }

  @Nullable
  public Integer getRewardPoints() {
    return rewardPoints;
  }

  @Nullable
  public List<Question> getQuestions() {
    return questions;
  }

  @Nullable
  public String getTargetLocationTag() {
    return targetLocationTag;
  }

  /**
   * Validates object state.
   *
   * @return Result.ok when object has valid internal state or Result.error
   * with enclosed exception explaining the cause.
   */
  public Result<Void, Exception> validate() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (tag == null) {
      builder.append("Null tag");
    } else if (tag.isBlank()) {
      builder.append("Blank tag");
    }

    if (actionTypeString == null) {
      builder.append("No action type provided");
    }

    return builder.isEmpty() ? Result.ok() :
        Result.err(new IllegalStateException(builder.toString()));
  }
}
