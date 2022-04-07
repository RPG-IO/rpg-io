package io.rpg;

import io.rpg.config.ConfigLoader;
import io.rpg.gui.DisplayLayer;
import io.rpg.model.GameObjectStandIn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Image someMap = new Image("file:assets/map.png");
    Image someMap10x10 = new Image("file:assets/map10x10.png");
    Image someDude1 = new Image("file:assets/someDude.png");
    Image someDude2 = new Image("file:assets/someDudeButGreen.png");

    DisplayLayer displayLayer = new DisplayLayer(stage);
    displayLayer.showLocation()
            .setBackgroundImage(someMap)
            .addMapObject(new GameObjectStandIn(new Pair<>(0,0), someDude1))
            .addMapObject(new GameObjectStandIn(new Pair<>(0,5), someDude1))
            .addMapObject(new GameObjectStandIn(new Pair<>(5,5), someDude2));
  }
}