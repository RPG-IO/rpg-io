package io.rpg;

import io.rpg.model.object.Player;
import io.rpg.util.Result;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.IOException;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    Configurator.setRootLevel(Level.DEBUG);
    Logger logger = LogManager.getLogger(Main.class);

    Initializer worldInitializer = new Initializer("configurations/demo-config-1", stage);
    Result<Game, Exception> initializationResult = worldInitializer.initialize();

    if (initializationResult.isErr()) {
      logger.error("Initialization error");

      initializationResult.getErrValueOpt().ifPresentOrElse(
          ex -> {
            logger.error(ex.getMessage());
            ex.printStackTrace();
          },
          () -> logger.error("No reason provided")
      );
      return;
    } else if (initializationResult.isOkValueNull()) {
      logger.error("Initialization returned null value");
      return;
    }

    // TODO: 04.05.2022 Null check for game was already made but IDE still screams
    Game game = initializationResult.getOkValue();
    game.start(stage);

    AnimationTimer animationTimer = new AnimationTimer() {
      long lastUpdate = -1;

      @Override
      public void handle(long now) {
        if (lastUpdate != -1) {
          float difference = (now - lastUpdate) / 1e6f;

          game.getController().getPlayerController().getPlayer().update(difference);
        }
        lastUpdate = now;
      }
    };
    animationTimer.start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
