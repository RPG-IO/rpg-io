package io.rpg.util;

import io.rpg.config.model.GameObjectConfig;

import java.lang.reflect.Field;
import java.util.Optional;

public class DataObjectDescriptionProvider {
  public static String getFieldDescription(Object object, Class clazz) {
    StringBuilder builder = new StringBuilder();
    for (Field field : clazz.getDeclaredFields()) {
      try {
        Optional<Object> fieldValue = Optional.ofNullable(field.get(object));
        fieldValue.ifPresent(_fieldValue -> builder.append('\t')
            .append(field.getName())
            .append(": ")
            .append(_fieldValue)
            .append(",\n")
        );
      } catch (IllegalAccessException ignored) { /* noop */ }
    }
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
