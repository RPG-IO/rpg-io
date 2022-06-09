package io.rpg.util;

public final class RuntimeConstantRegistryInstance {

  private static class StaticInitializer {
    private static final RuntimeConstantRegistry INSTANCE = new RuntimeConstantRegistry();
  }

  private RuntimeConstantRegistryInstance() {}

  public static RuntimeConstantRegistry get() {
    return StaticInitializer.INSTANCE;
  }
}
