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

public class LocationView extends Scene implements IObservable<IOnKeyPressedListener> {
  static final int SCALE = 32; // TODO: REMOVE THIS
  private final static URL FXML_URL = LocationViewModel.class.getResource("location-view.fxml");

  private final Logger logger;

  private final Set<IOnKeyPressedListener> onKeyPressedListeners;

  private final LocationViewModel viewModel;

  public LocationView(HBox root, LocationViewModel viewModel) {
    super(root);

    logger = LogManager.getLogger(LocationView.class);

    this.viewModel = viewModel;
    onKeyPressedListeners = new HashSet<>();

    this.setOnKeyPressed(this::onKeyPressed);
    this.setOnKeyReleased(this::onKeyReleased);

    System.out.println("CHILDREN");
    System.out.println(root.getChildrenUnmodifiable());

  }

  private void onKeyPressed(KeyEvent event) {
    logger.info("Collected key press event ");
    notifyOnKeyPressedListeners(event);
  }

  private void onKeyReleased(KeyEvent event) {
    logger.info("Collected key press");
    notifyOnKeyPressedListeners(event);
  }

  private void notifyOnKeyPressedListeners(KeyEvent event) {
    onKeyPressedListeners.forEach(listener -> listener.onKeyPressed(this, event));
  }

//  private void onKeyTyped(Key)

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
  public void addListener(IOnKeyPressedListener listener) {
    onKeyPressedListeners.add(listener);
  }

  @Override
  public void removeListener(IOnKeyPressedListener listener) {
    onKeyPressedListeners.remove(listener);
  }
}
