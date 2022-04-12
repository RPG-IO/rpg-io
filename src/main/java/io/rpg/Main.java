package io.rpg;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Configurator.setRootLevel(Level.DEBUG);
    Initializer worldInitializer = new Initializer("configurations/config-1", stage);
    Game game = worldInitializer.getWorld();

    stage.setScene(game.getWorldView());

    stage.show();
    System.out.println("SCENE");
    System.out.println(stage.getScene());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
