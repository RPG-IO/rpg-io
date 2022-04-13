package io.rpg.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Result<OkT, ErrorT> {
  @Nullable
  private final OkT okValue;

  @Nullable
  private final ErrorT errorValue;

  @NotNull
  private final Type type;

  private Result(@Nullable OkT okValue, @Nullable ErrorT errorValue, @NotNull Type type) {
    this.okValue = okValue;
    this.errorValue = errorValue;
    this.type = type;
  }

  public static <S, E> Result<S, E> ok(@Nullable S okValue) {
    return new Result<>(okValue, null, Type.OK);
  }

  public static <S, E> Result<S, E> error(@Nullable E errorValue) {
    return new Result<>(null, errorValue, Type.ERROR);
  }

  public boolean isError() {
    return type == Type.ERROR;
  }

  public boolean isOk() {
    return type == Type.OK;
  }

  @NotNull
  public Type getType() {
    return type;
  }

  @Nullable
  public OkT getOkValue() {
    if (isOk()) {
      return okValue;
    } else {
      throw new IllegalStateException("Attempt to access ok value on error result!");
    }
  }

  @Nullable
  public ErrorT getErrorValue() {
    if (isError()) {
      return errorValue;
    } else {
      throw new IllegalStateException("Attempt to access error value on ok result!");
    }
  }

  public enum Type {
    OK, ERROR
  }
}