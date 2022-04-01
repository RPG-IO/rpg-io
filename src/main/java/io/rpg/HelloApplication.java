package io.rpg;

import io.rpg.gui.DisplayLayer;
import io.rpg.model.GameObjectStandIn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Image someMap = new Image("C:\\Dev\\rpg-io\\assets\\map.png");
    Image someMap10x10 = new Image("C:\\Dev\\rpg-io\\assets\\map10x10.png");
    Image someDude1 = new Image("C:\\Dev\\rpg-io\\assets\\someDude.png");
    Image someDude2 = new Image("C:\\Dev\\rpg-io\\assets\\someDudeButGreen.png");

    DisplayLayer displayLayer = new DisplayLayer(stage);
    displayLayer.showLocation()
            .setBackgroundImage(someMap)
            .addMapObject(new GameObjectStandIn(new Pair<>(0,0), someDude1))
            .addMapObject(new GameObjectStandIn(new Pair<>(0,5), someDude1))
            .addMapObject(new GameObjectStandIn(new Pair<>(5,5), someDude2));

    new Thread(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Platform.runLater(() ->{
        displayLayer.showLocation()
                .setBackgroundImage(someMap10x10)
                .addMapObject(new GameObjectStandIn(new Pair<>(0,0), someDude1))
                .addMapObject(new GameObjectStandIn(new Pair<>(0,2), someDude1))
                .addMapObject(new GameObjectStandIn(new Pair<>(5,9), someDude2));
      });
    }).start();

  }

  public static void main(String[] args) {
    launch();
  }
}