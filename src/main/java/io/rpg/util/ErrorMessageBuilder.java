package io.rpg.util;

/**
 * Wrapper for {@link StringBuilder} instance.
 * It autoformats the error message on appending.
 */
public class ErrorMessageBuilder {
  private final static String formatString = "- ";
  private final StringBuilder builder;

  /**
   * Constructs {@link ErrorMessageBuilder} instance.
   */
  public ErrorMessageBuilder() {
    this.builder = new StringBuilder();
  }

  public ErrorMessageBuilder append(CharSequence sequence) {
    this.builder.append(formatString).append(sequence).append('\n');
    return this;
  }

  public ErrorMessageBuilder append(int n) {
    this.builder.append(formatString).append(n).append('\n');
    return this;
  }

  public ErrorMessageBuilder append(float n) {
    this.builder.append(formatString).append(n).append('\n');
    return this;
  }

  public ErrorMessageBuilder combine(String message) {
    this.builder.append(message);
    return this;
  }

  @Override
  public String toString() {
    return this.builder.toString();
  }

  public boolean isEmpty() {
    return this.builder.isEmpty();
  }
}
