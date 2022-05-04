package io.rpg.model.object;

public record Question(
  String question,
  String [] answers,
  char correctAnswer
) {}
