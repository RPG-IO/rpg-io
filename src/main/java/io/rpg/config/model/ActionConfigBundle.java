package io.rpg.config.model;

import com.google.gson.annotations.SerializedName;
import com.kkafara.rt.Result;
import io.rpg.model.actions.ActionType;
import io.rpg.model.data.Position;
import io.rpg.model.object.Question;
import io.rpg.util.ErrorMessageBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

/**
 * Represents action configuration.
 */
public class ActionConfigBundle implements ConfigWithValidation {
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
  private ActionType actionType;

  /**
   * Condition that needs to be satisfied before the action can be executed.
   */
  @Nullable
  @SerializedName(value = "condition", alternate = {"requires"})
  private ConditionConfigBundle condition;

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
   * {@link io.rpg.model.actions.CollectAction} <br>
   * TODO
   */
  @Nullable
  @SerializedName(value = "assetPath", alternate = {"asset", "asset-path"})
  private String assetPath;

  /**
   * {@link io.rpg.model.actions.GameEndAction} <br>
   * {@link io.rpg.model.actions.ShowDescriptionAction} <br>
   * {@link io.rpg.model.actions.CollectAction} <br>
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
   * {@link io.rpg.model.actions.LocationChangeAction} <br>
   * TODO
   */
  @Nullable
  @SerializedName(value = "targetPosition", alternate = {"target-position", "position"})
  private Position targetPosition;

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
  public ConditionConfigBundle getCondition() {
    return condition;
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
  public Position getTargetPosition() {
    return targetPosition;
  }

  @Nullable
  public String getTargetLocationTag() {
    return targetLocationTag;
  }

  Result<Void, Exception> validateForQuizAction() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (questions == null || questions.size() == 0) {
      builder.append("No questions provided");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
  }

  Result<Void, Exception> validateForDialogueAction() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (description == null) {
      builder.append("No description provided");
    }
    if (assetPath == null) {
      builder.append("No asset path provided");
    } else if (!Files.isRegularFile(Path.of(assetPath))) {
      builder.append("Provided asset path does not point to a regular file");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
  }

  Result<Void, Exception> validateForGameEndAction() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (description == null) {
      builder.append("No description provided");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
  }

  Result<Void, Exception> validateForLocationChangeAction() {
    // Note that the fact whether the location with provided tag exists is not checked.
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (targetLocationTag == null || targetLocationTag.isBlank()) {
      builder.append("Invalid location tag provided");
    }
    if (targetPosition ==  null) {
      builder.append("No target position provided");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
  }

  Result<Void, Exception> validateForShowDescriptionAction() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (description == null) {
      builder.append("No description provided");
    }
    if (assetPath == null) {
      builder.append("No asset path provided");
    } else if (!Files.isRegularFile(Path.of(assetPath))) {
      builder.append("Provided asset path does not point to a regular file");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
  }

  Result<Void, Exception> validateForBattle() {
    return Result.ok();
  }

  Result<Void, Exception> validateForBattleReflex() {
    return Result.ok();
  }

  Result<Void, Exception> validateForCollectAction() {
    ErrorMessageBuilder builder = new ErrorMessageBuilder();
    if (description == null) {
      builder.append("No description provided");
    }
    if (assetPath == null) {
      builder.append("No asset path provided");
    } else if (!Files.isRegularFile(Path.of(assetPath))) {
      builder.append("Provided asset path does not point to a regular file");
    }
    return builder.isEmpty() ? Result.ok() : Result.err(new IllegalStateException(builder.toString()));
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
    if (condition != null) {
      condition.validate().ifErr(ex -> builder.append(ex.getMessage()));
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
  @Override
  public Result<Void, Exception> validate() {
    Result<Void, Exception> result = validateBasic();

    if (result.isErr()) {
      return result;
    }

    if (beforeAction != null) {
      result = beforeAction.validate();
      if (result.isErr()) {
        return result;
      }
    }

    if (afterAction != null) {
      result = afterAction.validate();
      if (result.isErr()) {
        return result;
      }
    }

    // actionType can not be null here, guaranteed by validateBasic method
    //noinspection ConstantConditions
    switch (actionType) {
      case Dialogue -> { return validateForDialogueAction(); }
      case GameEnd -> { return validateForGameEndAction(); }
      case LocationChange -> { return validateForLocationChangeAction(); }
      case Quiz -> { return validateForQuizAction(); }
      case ShowDescription -> { return validateForShowDescriptionAction(); }
      case Battle -> { return validateForBattle(); }
      case BattleReflex -> { return validateForBattleReflex(); }
      case Collect -> { return validateForCollectAction(); }
      default -> { return Result.err(new RuntimeException("Invalid result returned")); }
    }
  }
}
