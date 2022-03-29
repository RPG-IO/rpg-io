package io.rpg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;

public class HelloApplication extends Application {
  // As start method is called even before main function, we need
  // to initialize logging even earlier
  @Override
  public void init() {
    setupLogging();

    Logger logger = LogManager.getLogger(HelloApplication.class);
    logger.info("Running application...");
  }

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    stage.setTitle("Hello!");
    stage.setScene(scene);
    stage.show();
  }

  public static void setupLogging() {
    // log everything
    Configurator.setRootLevel(Level.TRACE);
  }

  public static void main(String[] args) {
    launch();
  }
}
