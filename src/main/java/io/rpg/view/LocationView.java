package io.rpg.view;

import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.data.LocationModelStateChange;
import io.rpg.model.location.LocationModel;
import io.rpg.model.object.GameObject;
import io.rpg.viewmodel.LocationViewModel;
import io.rpg.config.model.LocationConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationView extends Scene
    implements KeyboardEvent.Emitter, LocationModelStateChange.Observer {
  private final static URL FXML_URL = LocationViewModel.class.getResource("location-view.fxml");

  private final Logger logger;

  private final Set<KeyboardEvent.Observer> onKeyPressedObservers;

  private final LocationViewModel viewModel;

  public LocationView(HBox root, LocationViewModel viewModel) {
    super(root);

    logger = LogManager.getLogger(LocationView.class);

    this.viewModel = viewModel;
    onKeyPressedObservers = new HashSet<>();

    this.setOnKeyPressed(event -> emitKeyboardEvent(new KeyboardEvent(this, event)));
    this.setOnKeyReleased(event -> emitKeyboardEvent(new KeyboardEvent(this, event)));

    System.out.println("CHILDREN");
    System.out.println(root.getChildrenUnmodifiable());
  }

  public LocationViewModel getViewModel() {
    return viewModel;
  }

  public static LocationView loadFromFXML(@NotNull URL fxmlUrl) throws IOException {
    FXMLLoader loader = new FXMLLoader(fxmlUrl);
    Parent parent = loader.load();
    LocationViewModel viewModel = loader.getController();
    assert parent == viewModel.getParent();
    return new LocationView(viewModel.getParent(), viewModel);
  }

  public static LocationView fromConfig(LocationConfig config) throws IOException {
    LocationView view = loadFromFXML(FXML_URL);
    System.out.println("BACKGROUND PATH");
    System.out.println(config.getBackgroundPath());
    view.getViewModel().setBackground(new Image(resolvePathToJFXFormat(config.getBackgroundPath())));
    return view;
  }

  public static String resolvePathToJFXFormat(String path) {
    return "file:" + path;
  }

  @Override
  public void addKeyboardEventObserver(KeyboardEvent.Observer observer) {
    onKeyPressedObservers.add(observer);
  }

  @Override
  public void removeKeyboardEventObserver(KeyboardEvent.Observer observer) {
    onKeyPressedObservers.remove(observer);
  }

  @Override
  public void emitKeyboardEvent(KeyboardEvent event) {
    List<KeyboardEvent.Observer> observers = new ArrayList<>(onKeyPressedObservers);
    observers.forEach(observer -> {
      observer.onKeyboardEvent(event);
    });
  }

  @Override
  public void onLocationModelStateChange(LocationModelStateChange event) {
    // TODO: implement state change & appropriate events
    // most likely here we want to pass this event to LocationViewModel or even
    // make LocationViewModel implement LocationModelStateChange.Observer
  }


  List<GameObjectView> gameObjectViews = new ArrayList<>();


  public void removeChild(GameObjectView view) {
    viewModel.getForegroundPane().getChildren().remove(view);
  }

  public void addChild(GameObjectView view) {
    viewModel.getForegroundPane().getChildren().add(view);
  }
}
