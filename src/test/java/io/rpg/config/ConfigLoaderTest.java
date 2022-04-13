package io.rpg.config;

import io.rpg.config.model.GameWorldConfig;

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

    List<String> actualLocationNames = config.getLocationTags();
    Assertions.assertEquals(expectedLocationNames, actualLocationNames);
  }

//  @Test
//  public void LocationConfigIsLoadedProperly() throws FileNotFoundException {
//    ConfigLoader configLoader = new ConfigLoader(CFG_DIR_PATH);
//    String locationTag = "location-1";
//
//    Assertions.assertDoesNotThrow(() -> {
//      configLoader.loadLocation(locationTag);
//    });
//
//    LocationConfig config = configLoader.loadLocation(locationTag);
//    Assertions.assertNotNull(config);
//
//    Assertions.assertEquals(locationTag, config.getTag());
//
//    GameObjectConfig expectedGameObject1 = new GameObjectConfig("object-1", new Position(0, 5));
//    GameObject expectedGameObject2 = new GameObject("object-2", new Position(1, 3));
//
//    // this test relies on order of deserialization in Gson implementation
//    // todo: find a better way to test this
//
//    List<GameObject> actualGameObjects = config.getObjects();
//
//    Assertions.assertEquals(2, actualGameObjects.size());
//
//    GameObject actualGameObject1 = actualGameObjects.get(0);
//    GameObject actualGameObject2 = actualGameObjects.get(1);
//
//    Assertions.assertEquals(expectedGameObject1.getTag(), actualGameObject1.getTag());
//    Assertions.assertEquals(expectedGameObject2.getTag(), actualGameObject2.getTag());
//    Assertions.assertEquals(expectedGameObject1.getPosition(), actualGameObject1.getPosition());
//    Assertions.assertEquals(expectedGameObject2.getPosition(), actualGameObject2.getPosition());
//  }
}
