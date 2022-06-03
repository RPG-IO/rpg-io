package io.rpg.wrapper;

import java.io.File;
import java.util.Optional;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ConfigChooser {
  private final FileChooser chooser;

  public ConfigChooser() {
    chooser = new FileChooser();
    chooser.setTitle("Select root.json");
    chooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("config", "root.json"),
        new FileChooser.ExtensionFilter("json", "*.json")
    );
  }

  /**
   * Open file selection window.

   * @param stage - main stage.
   * @return Optional of selected file folder path.
   */
  public Optional<String> open(Stage stage) {
    File file = chooser.showOpenDialog(stage);
    if (file == null) {
      return Optional.empty();
    }

    return Optional.of(file.getParentFile().getAbsolutePath());
  }
}
