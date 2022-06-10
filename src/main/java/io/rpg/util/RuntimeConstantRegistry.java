package io.rpg.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class RuntimeConstantRegistry {
  private final Logger logger = LogManager.getLogger(RuntimeConstantRegistry.class);

  private Path assetDirPath = null;

  RuntimeConstantRegistry() {}

  @NotNull
  public Path getAssetDirPath() {
    if (assetDirPath != null) return assetDirPath;
    throw new IllegalStateException("Attempt to get asset dir before it was set");
  }

  public void setAssetDirPath(Path assetDirPath) {
    if (this.assetDirPath != null) {
      logger.warn("Overwriting asset directory path in constant registry. Old value: " + this.assetDirPath
          + ". New value: " + assetDirPath);
    }
    this.assetDirPath = assetDirPath;
  }
}
