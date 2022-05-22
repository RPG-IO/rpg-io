package io.rpg.controller;

import io.rpg.model.actions.Action;
import io.rpg.model.actions.ActionConsumer;
import io.rpg.model.actions.GameEndAction;
import io.rpg.model.actions.LocationChangeAction;
import io.rpg.model.actions.QuizAction;
import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.data.MouseClickedEvent;
import io.rpg.model.data.Position;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Question;
import io.rpg.util.Result;
import io.rpg.view.GameEndView;
import io.rpg.view.GameObjectView;
import io.rpg.view.LocationView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Controller implements KeyboardEvent.Observer, MouseClickedEvent.Observer, ActionConsumer {
  private Scene currentView;
  private LinkedHashMap<String, LocationModel> tagToLocationModelMap;
  private LocationModel currentModel;
  private LinkedHashMap<String, LocationView> tagToLocationViewMap;
  private Logger logger;
  private final PopupController popupController = new PopupController();
  private PlayerController playerController;
  private Stage mainStage;


  public Controller() {
    logger = LogManager.getLogger(Controller.class);

    tagToLocationModelMap = new LinkedHashMap<>();
    tagToLocationViewMap = new LinkedHashMap<>();
  }

  public Controller(LinkedHashMap<String, LocationModel> tagToLocationModelMap,
                    LinkedHashMap<String, LocationView> tagToLocationViewMap,
                    String rootTag) {
    logger = LogManager.getLogger(Controller.class);

    this.tagToLocationViewMap = tagToLocationViewMap;
    this.tagToLocationModelMap = tagToLocationModelMap;

    // TODO: handle errors
    this.currentModel = this.tagToLocationModelMap.get(rootTag);
    this.currentView = this.tagToLocationViewMap.get(rootTag);
  }

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
  }

  public void setModel(@NotNull LocationModel model) {
    this.tagToLocationModelMap.put(model.getTag(), model);
    currentModel = model;
  }

  public void setView(Scene currentView) {
    this.currentView = currentView;
  }

  @Override
  public void consumeAction(Action action) {
    Class<?>[] args = {action.getClass()};
    Method onSpecificAction;

    try {
      onSpecificAction = this.getClass().getDeclaredMethod("onAction", args);
    } catch (NoSuchMethodException e) {
      logger.error("onAction for " + action.getClass().getSimpleName() + "is not implemented");
      return;
    }

    try {
      onSpecificAction.invoke(this, action);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private void onAction(LocationChangeAction action) {
    if (!this.tagToLocationModelMap.containsKey(action.destinationLocationTag)) {
      logger.error("Unknown location tag");
      return;
    }

    LocationView nextView = this.tagToLocationViewMap.get(action.destinationLocationTag);
    LocationModel nextModel = this.tagToLocationModelMap.get(action.destinationLocationTag);

    playerController.teleportTo(nextModel, nextView, action.playerPosition);

    this.currentModel = nextModel;
    this.currentView = nextView;
    mainStage.setScene(nextView);
  }


  private void onAction(QuizAction action) {
    int pointsCount = action.getPointsToEarn();
    popupController.openQuestionPopup(
        action.question,
        getWindowCenterX(), getWindowCenterY(),
        () -> acceptQuizResult(true, pointsCount),
        () -> acceptQuizResult(false, 0)
    );
    action.setPointsToEarn(0);
  }

  public void acceptQuizResult(boolean correct, int pointsCount) {
    if (correct) {
      playerController.addPoints(pointsCount);
      popupController.hidePopup();
      if (pointsCount > 0)
        popupController.openPointsPopup(pointsCount, getWindowCenterX(), getWindowCenterY());
    } else {
      popupController.hidePopup();
      System.out.println("wrong answer");
    }
  }

  private void onAction(GameEndAction action) {
    GameEndView view = GameEndView.load();
    view.setDescription(action.description);
    double prevWidth = mainStage.getWidth();
    double prevHeight = mainStage.getHeight();
    mainStage.setScene(view);
    mainStage.setWidth(prevWidth);
    mainStage.setHeight(prevHeight);
  }

  public Scene getView() {
    return currentView;
  }

  public void setTagToLocationModelMap(LinkedHashMap<String, LocationModel> tagToLocationModelMap) {
    this.tagToLocationModelMap = tagToLocationModelMap;
  }

  public void setTagToLocationViewMap(LinkedHashMap<String, LocationView> tagToLocationViewMap) {
    this.tagToLocationViewMap = tagToLocationViewMap;
  }

  public LinkedHashMap<String, LocationModel> getTagToLocationModelMap() {
    return tagToLocationModelMap;
  }

  public LinkedHashMap<String, LocationView> getTagToLocationViewMap() {
    return tagToLocationViewMap;
  }

  public Result<Controller, Exception> validate() {
    if (tagToLocationModelMap.size() == 0)
      return Result.error(new Exception("Empty tag to location model map!"));
    else if (tagToLocationViewMap.size() == 0)
      return Result.error(new Exception("Empty tag to location view map!"));
    else if (tagToLocationViewMap.size() != tagToLocationModelMap.size())
      return Result.error(new Exception("Mismatched sizes of maps!"));
    else
      return Result.ok(this);
  }

  @Override
  public void onKeyboardEvent(KeyboardEvent event) {
    // TODO: implement event handling
    logger.trace("Controller notified on key pressed from " + event.source());
    //TODO: call Player::set...Pressed depending on keyCode and whether the key was pressed or released

    KeyEvent payload = event.payload();

    if (payload.getEventType() == KeyEvent.KEY_PRESSED) {
      switch (payload.getCode()) {
        case F -> popupController.openPointsPopup(5, getWindowCenterX(), getWindowCenterY());
        case G -> popupController.openTextPopup("Hello!", getWindowCenterX(), getWindowCenterY());
        case Q -> popupController.openQuestionPopup(new Question("How many bits are there in one byte?", new String[]{"1/8", "1024", "8", "256"}, 'C'), getWindowCenterX(), getWindowCenterY());
        case L -> consumeAction(new LocationChangeAction("location-2", new Position(1, 2)));
        case U -> consumeAction(new GameEndAction("You have pressed the forbidden button"));
      }
    }
    // } else if (payload.getEventType() == KeyEvent.KEY_RELEASED) {
    //
    // }
  }

  private int getWindowCenterX() {
    return (int) (currentView.getWindow().getX() + currentView.getWindow().getWidth() / 2);
  }

  private int getWindowCenterY() {
    return (int) (currentView.getWindow().getY() + currentView.getWindow().getHeight() / 2);
  }

  @Override
  public void onMouseClickedEvent(MouseClickedEvent event) {
    Point2D playerPos = playerController.getPlayer().getExactPosition();
    GameObjectView objectView = event.source();
    Position position = new Position(objectView.getPosition());
    GameObject object = currentModel.getObject(position)
                                    .orElseThrow(() -> new RuntimeException("No object present at position " + position));

    double distance = playerPos.distance(objectView.getPosition());

    if (distance < 1.5) {
      MouseButton button = event.payload().getButton();
      if (button.equals(MouseButton.PRIMARY)) {
        object.onLeftClick();
      } else if (button.equals(MouseButton.SECONDARY)) {
        object.onRightClick();
      }
    }

    logger.info("Controller notified on click from " + event.source());
  }

  public PlayerController getPlayerController() {
    return playerController;
  }


  public static class Builder {
    private final Controller controller;

    public Builder() {
      controller = new Controller();
    }

    public Controller build() {
      Result<Controller, Exception> validationResult = controller.validate();
      if (validationResult.isError()) {
        throw new IllegalStateException(validationResult.getErrorValue());
      }

      controller.tagToLocationModelMap.values().forEach(location -> location.setActionConsumer(controller));
      controller.playerController.getPlayer().setActionConsumer(controller);

      return controller;
    }

    public Builder registerToViews(List<GameObjectView> views) {
      for (GameObjectView view : views) {
        view.addOnClickedObserver(controller);
      }
      return this;
    }

    public Builder addViewForTag(String tag, LocationView view) {
      controller.getTagToLocationViewMap().put(tag, view);
      view.addKeyboardEventObserver(controller);
      return this;
    }

    public Builder addModelForTag(String tag, LocationModel model) {
      controller.getTagToLocationModelMap().put(tag, model);
      return this;
    }

    public void setPlayerController(PlayerController playerController) {
      controller.playerController = playerController;
    }
  }
}
