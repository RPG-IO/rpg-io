package io.rpg.config;

import io.rpg.config.model.GameWorldConfig;

import io.rpg.util.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigLoaderTest {
  private static final String configDirPath = "configurations/unit-test-configurations";
  private static final Path configurationsPath = Path.of(configDirPath);


  @BeforeAll
  public static void validateTestConfiguration() {
    if (!Files.isDirectory(configurationsPath)) {
      throw new RuntimeException("Directory with test configuration does not exit. Provided path: "
          + configurationsPath);
    }
  }

  @Test
  public void ConfigLoaderDoesThrowWithBadDirPath() {
    final Path notExistingConfigPath = configurationsPath.resolve("not-exiting-config");

    if (Files.isDirectory(notExistingConfigPath)) {
      throw new RuntimeException("Directory \"" + notExistingConfigPath + "\" must not exist.");
    }

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ConfigLoader configLoader = new ConfigLoader(notExistingConfigPath);
    });
  }

  @Test
  public void ConfigLoaderDoesNotThrowWithGoodDirPath() {
    Path existingConfigPath = configurationsPath.resolve("test-config-3-minimal-struct");

    if (!Files.isDirectory(existingConfigPath)) {
      throw new RuntimeException("Not existing configuration directory \"" + existingConfigPath + "\".");
    }

    Assertions.assertDoesNotThrow(() -> {
      ConfigLoader configLoader = new ConfigLoader(existingConfigPath);
    });
  }

  @Test
  public void GameWorldConfigIsLoadedProperly() {
    Path fullConfigPath = configurationsPath.resolve("test-config-1-full");

    if (!Files.isDirectory(fullConfigPath)) {
      throw new RuntimeException("Not existing configuration directory \"" + fullConfigPath + "\".");
    }

    List<String> expectedLocationNames = List.of("location-1", "location-2");
    String testTag = "test-config-1-full";

    ConfigLoader configLoader = new ConfigLoader(fullConfigPath);
    Result<GameWorldConfig, Exception> loadingResult = configLoader.loadGameWorldConfig();

    GameWorldConfig config = loadingResult.getOkValue();

    Assertions.assertNotNull(config);

    Assertions.assertEquals(testTag, config.getTag());

    List<String> actualLocationNames = config.getLocationTags();
    Assertions.assertEquals(expectedLocationNames, actualLocationNames);
  }
}
