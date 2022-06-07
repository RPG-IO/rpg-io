package io.rpg;

import com.kkafara.rt.Result;
import io.rpg.wrapper.WrapperController;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Entry point of the app.
 */
public class Main extends Application {
  private final Logger logger = LogManager.getLogger(Main.class);

  @Override
  public void start(Stage stage) throws IOException {
    Configurator.setRootLevel(Level.WARN);
    String path = getParameters().getNamed().get("config");
    if (path == null) {
      WrapperController wrapperController = WrapperController.load();
      wrapperController.show(stage);
    } else {
      fastStart(path, stage);
    }
  }

  private void fastStart(String path, Stage stage) {
    Initializer worldInitializer = new Initializer(path);
    Result<Game, Exception> initializationResult = worldInitializer.initialize();

    if (initializationResult.isErr()) {
      logger.error("Initialization error");

      initializationResult.getErrOpt().ifPresentOrElse(
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

    Game game = initializationResult.getOk();
    game.start(stage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
