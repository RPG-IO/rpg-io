package io.rpg.controller;

import com.kkafara.rt.Result;
import io.rpg.model.actions.*;
import io.rpg.model.actions.condition.ConditionEngine;
import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.data.MouseClickedEvent;
import io.rpg.model.data.Position;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.model.object.Question;
import io.rpg.view.GameObjectView;
import io.rpg.view.LocationView;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

public class Controller implements KeyboardEvent.Observer, MouseClickedEvent.Observer, ActionConsumer {
  private Scene currentView;
  private LinkedHashMap<String, LocationModel> tagToLocationModelMap;
  private LocationModel currentModel;
  private LinkedHashMap<String, LocationView> tagToLocationViewMap;
  private Logger logger;
  private final PopupController popupController = new PopupController();
  private PlayerController playerController;
  private GameEndController gameEndController;
  private Stage mainStage;
  private final ConditionEngine conditionEngine;
  private final ActionEngine actionEngine;

  public Controller() {
    logger = LogManager.getLogger(Controller.class);
    tagToLocationModelMap = new LinkedHashMap<>();
    tagToLocationViewMap = new LinkedHashMap<>();

    conditionEngine = new ConditionEngine(this);
    actionEngine = new ActionEngine(this);
    gameEndController = new GameEndController();
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

    conditionEngine = new ConditionEngine(this);
    actionEngine = new ActionEngine(this);
  }

  public void setMainStage(Stage mainStage) {
    this.mainStage = mainStage;
    this.popupController.setOwner(mainStage);

    if (mainStage.getScene() != null) {
      mainStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> popupController.hidePopup());
    }
  }

  public void stopPlayer() {
    var player = playerController.getPlayer();
    player.setDownPressed(false);
    player.setUpPressed(false);
    player.setLeftPressed(false);
    player.setRightPressed(false);
  }

  public void setCurrentModel(@NotNull LocationModel model) {
    currentModel = model;
  }

  public Stage getMainStage() {
    return mainStage;
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
    if (Action.VOID.equals(action)) {
      logger.trace("Consumed void action");
      return;
    }
    logger.info("Consuming action");
    action.acceptActionEngine(actionEngine);
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
      return Result.err(new Exception("Empty tag to location model map!"));
    else if (tagToLocationViewMap.size() == 0)
      return Result.err(new Exception("Empty tag to location view map!"));
    else if (tagToLocationViewMap.size() != tagToLocationModelMap.size())
      return Result.err(new Exception("Mismatched sizes of maps!"));
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
        case E ->
            popupController.openInventoryPopup(playerController.getPlayer().getInventory(), getWindowCenterX(), getWindowCenterY(), playerController.getPlayer());
      }
    }
  }

  public int getWindowCenterX() {
    return (int) (currentView.getWindow().getX() + currentView.getWindow().getWidth() / 2);
  }

  public int getWindowCenterY() {
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

  public void removeObjectFromModel(GameObject object) {
    String tag = currentModel.getTag();
    LocationView currentLocationView = tagToLocationViewMap.get(tag);
    currentLocationView.removeViewBoundToObject(object);
    currentModel.removeGameObject(object);
  }

  public PlayerController getPlayerController() {
    return playerController;
  }

  public GameEndController getGameEndController() {
    return gameEndController;
  }

  public PopupController getPopupController() {
    return popupController;
  }

  public ConditionEngine getConditionEngine() {
    return conditionEngine;
  }

  public static class Builder {
    private final Controller controller;

    public Builder() {
      controller = new Controller();
    }

    public Controller build() {
      Result<Controller, Exception> validationResult = controller.validate();
      if (validationResult.isErr()) {
        throw new IllegalStateException(validationResult.getErr());
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
