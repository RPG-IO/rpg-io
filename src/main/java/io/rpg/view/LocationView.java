package io.rpg.view;

import io.rpg.config.model.LocationConfig;
import io.rpg.model.data.KeyboardEvent;
import io.rpg.model.object.GameObject;
import io.rpg.viewmodel.LocationViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.rpg.util.PathUtils.resolvePathToJFXFormat;

public class LocationView extends Scene
    implements KeyboardEvent.Emitter {
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
    view.getViewModel().setBackground(new Image(resolvePathToJFXFormat(config.getBackgroundPath())));
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
    List<KeyboardEvent.Observer> observers = new ArrayList<>(onKeyPressedObservers);
    observers.forEach(observer -> {
      observer.onKeyboardEvent(event);
    });
  }

  List<GameObjectView> gameObjectViews = new ArrayList<>();

  public void removeChild(GameObjectView view) {
    viewModel.getForegroundPane().getChildren().remove(view);
  }

  public void addChild(GameObjectView view) {
    viewModel.getForegroundPane().getChildren().add(view);
  }

  public GameObjectView findViewBoundToObject(GameObject gameObject){
    for(Node node : viewModel.getForegroundPane().getChildren()) {
      if (node instanceof GameObjectView) {
        GameObjectView temp = (GameObjectView) node;
        if (temp.getBoundObject() == gameObject) {
          return temp;
        }
      }
    }
    return null;
  }

  public void removeViewBoundToObject(GameObject gameObject){
    GameObjectView view = findViewBoundToObject(gameObject);
    if(view != null) {
      removeChild(view);
    }
//    for(Node node : viewModel.getForegroundPane().getChildren()){
//      if(node instanceof GameObjectView){
//          GameObjectView temp = (GameObjectView) node;
//          if(temp.getBoundObject() == gameObject){
//            removeChild(temp);
//            break;
//          }
//      }
//    }
  }
  
}
