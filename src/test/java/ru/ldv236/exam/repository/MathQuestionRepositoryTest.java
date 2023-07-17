package ru.ldv236.exam.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.exception.QuestionNotFoundException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathQuestionRepositoryTest {

    private QuestionRepository out;

    @BeforeEach
    public void setUp() {
        this.out = new MathQuestionRepository();
    }

    static Stream<Arguments> provideParamsForTests() {
        return Stream.of(
                Arguments.of(new Question("MathQuestion1", "MathAnswer1")),
                Arguments.of(new Question("MathQuestion2", "MathAnswer2")),
                Arguments.of(new Question("MathQuestion3", "MathAnswer3")),
                Arguments.of(new Question("MathQuestion4", "MathAnswer4")),
                Arguments.of(new Question("MathQuestion5", "MathAnswer5"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    void shouldAddAndRemoveNewQuestion(Question question) {

        Question actual = out.add(question);
        assertEquals(question, actual);

        Question actual2 = out.remove(question);
        assertEquals(question, actual2);
    }

    @Test
    void shouldAddOnlyOneSameQuestionAndRemoveIt() {

        for (int i = 0; i < 10; i++) {
            out.add(new Question("MathQuestion", "MathAnswer"));
            assertEquals(1, out.getAll().size());
        }

        out.remove(new Question("MathQuestion", "MathAnswer"));
        assertEquals(0, out.getAll().size());
    }

    @Test
    void shouldAddTenQuestionsAndRemoveItWithSizeCheck() {

        for (int i = 0; i < 10; i++) {
            out.add(new Question("MathQuestion" + i, "MathAnswer"));
            assertEquals(i + 1, out.getAll().size());
        }

        for (int i = 0; i < 10; i++) {
            out.remove(new Question("MathQuestion" + i, "MathAnswer"));
            assertEquals(9 - i, out.getAll().size());
        }
    }

    @Test
    void shouldThrowQuestionNotFoundException() {
        assertThrows(QuestionNotFoundException.class,
                () -> out.remove(new Question("not", "exist")));
    }

    @Test
    void shouldAddTenQuestionsAndCheckResultSet() {

        Set<Question> expectedSet = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            out.add(new Question("MathQuestion" + i, "MathAnswer"));
            expectedSet.add(new Question("MathQuestion" + i, "MathAnswer"));
        }
        assertEquals(expectedSet, out.getAll());
    }
}
