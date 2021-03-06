package io.rpg.wrapper;

import com.kkafara.rt.Result;
import io.rpg.Game;
import io.rpg.Initializer;
import io.rpg.Main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import io.rpg.config.ConfigConstants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Adds start and load screens.
 */
public class WrapperController {
  private final Logger logger = LogManager.getLogger(WrapperController.class);
  @FXML private BorderPane mainPane;
  @FXML private TextArea outputArea;
  @FXML private Button startButton;
  private Stage stage;
  private Scene scene;
  private Parent startView;
  private Parent loadView;

  private final ConfigChooser chooser;
  private Game game;

  public WrapperController() {
    chooser = new ConfigChooser();
  }

  public static WrapperController load() throws IOException {
    FXMLLoader loader = new FXMLLoader(WrapperController.class.getResource("main-view.fxml"));
    loader.load();
    WrapperController controller = loader.getController();

    controller.loadStartView();
    controller.loadLoadView();

    controller.createScene();
    return controller;
  }

  private void loadStartView() throws IOException {
    FXMLLoader loader = new FXMLLoader(WrapperController.class.getResource("start-view.fxml"));
    loader.setController(this);
    this.startView = loader.load();
  }

  private void loadLoadView() throws IOException {
    FXMLLoader loader = new FXMLLoader(WrapperController.class.getResource("load-view.fxml"));
    loader.setController(this);
    this.loadView = loader.load();
  }

  private void createScene() {
    scene = new Scene(mainPane);
  }

  public void show(Stage stage) {
    this.stage = stage;
    showView(startView);
    stage.setScene(scene);
    stage.show();
  }

  private void showView(Parent view) {
    mainPane.setCenter(view);
  }

  @FXML
  private void onBeginClick() {
    showView(loadView);
  }

  @FXML
  private void onExitClick() {
    stage.close();
  }

  @FXML
  private void onBackClick() {
    showView(startView);
  }

  @FXML
  private void onLoadClick() {
    Optional<String> pathOptional = this.chooser.open(stage);
    if (pathOptional.isEmpty()) {
      printLine("No file was selected");
      startButton.setDisable(true);
      return;
    }

    String path = pathOptional.get();
    printLine("Selected: " + path);

    // simple check if the user did not pick just a random file
    Path _path = Path.of(path);
    if (!Files.isReadable(_path) || !Files.isDirectory(_path)) {
      printLine(_path.toString() + " is not readable or is not a directory");
    } else {
      startButton.setDisable(false);
      loadGame(path).ifOk(
          g -> {
            game = g;
//            game.setOnEnd(() -> show(stage));
          }
      );
    }
  }

  private Result<Game, Void> loadGame(String path) {
    Initializer worldInitializer = new Initializer(path);
    Result<Game, Exception> initializationResult = worldInitializer.initialize();

    if (initializationResult.isErr()) {
      logger.error("Initialization error");
      printLine("Initialization error");

      initializationResult.getErrOpt().ifPresentOrElse(
          ex -> {
            logger.error(ex.getMessage());
            printLine(ex.getMessage());
            ex.printStackTrace();
          },
          () -> printLine("No reason provided")
      );
      return Result.err();
    } else if (initializationResult.isOkValueNull()) {
      printLine("Initialization returned null value");
      return Result.err();
    }

    return Result.ok(initializationResult.getOk());
  }

  @FXML
  private void onStartClick() {
    if (game != null) {
      startButton.setDisable(true);
      game.start(stage);
    }
  }

  private void printLine(String line) {
    outputArea.setText(outputArea.getText() + "\n" + line);
  }
}
