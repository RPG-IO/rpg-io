package io.rpg.config;

import io.rpg.model.GameWorldConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public class ConfigLoaderTest {
  private static final String CFG_DIR_PATH = "configurations/unit-test-configuration";

  @Test
  public void ConfigLoaderDoesThrowWithBadDirPath() {
    String notExistingPath = "/kokoko/xd/oko";

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ConfigLoader configLoader = new ConfigLoader(notExistingPath);
    });
  }

  @Test
  public void ConfigLoaderDoesNotThrowWithGoodDirPath() {
    Assertions.assertDoesNotThrow(() -> {
      ConfigLoader configLoader = new ConfigLoader(CFG_DIR_PATH);
    });
  }

  @Test
  public void GameWorldConfigIsLoadedProperly() throws FileNotFoundException {
    List<String> expectedLocationNames = List.of("location-1", "location-2");
    String testTag = "test-tag";

    ConfigLoader configLoader = new ConfigLoader(CFG_DIR_PATH);
    GameWorldConfig config = configLoader.loadGameWorldConfig();

    Assertions.assertEquals(testTag, config.getTag());

    List<String> actualLocationNames = config.getLocations();
    Assertions.assertEquals(expectedLocationNames, actualLocationNames);
  }
}
