package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import io.rpg.model.actions.ActionType;
import io.rpg.model.object.Question;
import io.rpg.util.ErrorMessageBuilder;
import io.rpg.util.Result;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents action configuration.
 */
public class ActionConfigBundle {
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
//  private String actionType;
  private ActionType actionType;

  /**
   * Action to be triggered before the proper action is executed.
   */
  @Nullable
  @SerializedName(value = "before", alternate = {"beforeAction", "before-action"})
  private ActionConfigBundle beforeAction;

  /**
   * Action to be triggered after the proper action had been executed.
   */
  @Nullable
  @SerializedName(value = "after", alternate = {"afterAction", "after-action"})
  private ActionConfigBundle afterAction;

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
  ActionConfigBundle(@NotNull String tag, @NotNull ActionType actionType) {
    this.tag = tag;
    this.actionType = actionType;
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
  public ActionType getActionType() {
    return actionType;
  }

  @Nullable
  public ActionConfigBundle getBeforeAction() {
    return beforeAction;
  }

  @Nullable
  public ActionConfigBundle getAfterAction() {
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

  Result<Void, Exception> validateForQuizAction() {
    return Result.ok();
  }

  Result<Void, Exception> validateForDialogueAction() {
    return Result.ok();
  }

  Result<Void, Exception> validateForGameEndAction() {
    return Result.ok();
  }

  Result<Void, Exception> validateForLocationChangeAction() {
    return Result.ok();
  }

  Result<Void, Exception> validateForShowDescriptionAction() {
    return Result.ok();
  }

  Result<Void, Exception> validateBasic() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (tag == null) {
      builder.append("Null tag");
    } else if (tag.isBlank()) {
      builder.append("Blank tag");
    }

    if (actionType == null) {
      builder.append("No action type or invalid action type provided");
    }

    return builder.isEmpty() ? Result.ok() :
        Result.err(new IllegalStateException(builder.toString()));
  }

  /**
   * Validates object state.
   *
   * @return Result.ok when object has valid internal state or Result.error
   * with enclosed exception explaining the cause.
   */
  public Result<Void, Exception> validate() {
    Result<Void, Exception> result = validateBasic();

    if (result.isErr()) {
      return result;
    }

    // actionType can not be null here, guaranteed by validateBasic method
    //noinspection ConstantConditions
    switch (actionType) {
      case Dialogue -> { return validateForDialogueAction(); }
      case GameEnd -> { return validateForGameEndAction(); }
      case LocationChange -> { return validateForLocationChangeAction(); }
      case Quiz -> { return validateForQuizAction(); }
      case ShowDescription -> { return validateForShowDescriptionAction(); }
      default -> { return Result.err(new RuntimeException("Invalid result returned")); }
    }
  }
}
