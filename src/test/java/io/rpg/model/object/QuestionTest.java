package io.rpg.model.object;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class QuestionTest {

  private final Gson gson = new Gson();


  @Test
  public void testDeserialization() {
    String json = """
        {
            "header": "Test question",
            "a": "1",
            "b": "2",
            "c": "3",
            "d": "4",
            "correct": "b"
        }""";

    Question question = gson.fromJson(json, Question.class);

    Assertions.assertEquals(question.getQuestion(), "Test question");
    Assertions.assertEquals(question.getAnswerA(), "1");
    Assertions.assertEquals(question.getAnswerB(), "2");
    Assertions.assertEquals(question.getAnswerC(), "3");
    Assertions.assertEquals(question.getAnswerD(), "4");
    Assertions.assertEquals(question.getCorrectAnswerChar(), 'B');
    Assertions.assertEquals(question.getCorrectAnswer(), "2");
  }

  @Test
  public void testAlternateFieldNaming() {
    String json = """
        {
            "header": "Test question",
            "A": "1",
            "B": "2",
            "c": "3",
            "D": "4",
            "correct": "b"
        }""";

    Question question = gson.fromJson(json, Question.class);

    Assertions.assertEquals(question.getQuestion(), "Test question");
    Assertions.assertEquals(question.getAnswerA(), "1");
    Assertions.assertEquals(question.getAnswerB(), "2");
    Assertions.assertEquals(question.getAnswerC(), "3");
    Assertions.assertEquals(question.getAnswerD(), "4");
    Assertions.assertEquals(question.getCorrectAnswerChar(), 'B');
    Assertions.assertEquals(question.getCorrectAnswer(), "2");
  }

  @Test
  public void testGetCorrectAnswer() {
    String json = """
        {
            "header": "Test question",
            "A": "1",
            "B": "2",
            "c": "3",
            "D": "4",
            "correct": "X"
        }""";

    Question question = gson.fromJson(json, Question.class);
    Assertions.assertThrows(IllegalStateException.class, question::getCorrectAnswer);
  }

}