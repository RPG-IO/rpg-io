package io.rpg.view;

import io.rpg.viewmodel.LocationViewModel;
import io.rpg.config.model.LocationConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class LocationView extends Scene implements KeyboardEvent.Emitter, ILocationModelStateChangeObserver {
  static final int SCALE = 32; // TODO: REMOVE THIS
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
    view.getViewModel().setBackground(new Image(config.getBackgroundPath()));
    // todo: na podstawie configu ustawić pola korzystając z view modelu
    return view;
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
    onKeyPressedObservers.forEach(observer -> {
      observer.onKeyboardEvent(event);
    });
  }
}
