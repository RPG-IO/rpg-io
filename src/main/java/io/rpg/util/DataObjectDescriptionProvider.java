package io.rpg.util;

import java.util.Arrays;
import java.util.Optional;

public class DataObjectDescriptionProvider {
  public static String getFieldDescription(Object object, Class<?> clazz) {
    StringBuilder builder = new StringBuilder();
    Arrays.stream(clazz.getDeclaredFields()).filter(field -> !field.getName().startsWith("$")).forEach(field -> {
      try {
        field.setAccessible(true);
        Optional.ofNullable(field.get(object)).ifPresent(value -> builder.append('\t')
            .append(field.getName())
            .append(": ")
            .append(value)
            .append(",\n")
        );
      } catch (IllegalAccessException ignored) { /* noop */ }
    });
    return builder.toString();
  }

  public static String combineDescriptions(String... description) {
    StringBuilder builder = new StringBuilder();
    builder.append("\n{\n");
    for (String desc : description) {
      builder.append(desc);
    }
    return builder.append("}").toString();
  }
}
