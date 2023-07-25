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

public class JavaQuestionRepositoryTest {

    private QuestionRepository out;

    @BeforeEach
    public void setUp() {
        this.out = new JavaQuestionRepository();
    }

    static Stream<Arguments> provideParamsForTests() {
        return Stream.of(
                Arguments.of(new Question("JavaQuestion1", "JavaAnswer1")),
                Arguments.of(new Question("JavaQuestion2", "JavaAnswer2")),
                Arguments.of(new Question("JavaQuestion3", "JavaAnswer3")),
                Arguments.of(new Question("JavaQuestion4", "JavaAnswer4")),
                Arguments.of(new Question("JavaQuestion5", "JavaAnswer5"))
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
            out.add(new Question("JavaQuestion", "JavaAnswer"));
            assertEquals(1, out.getAll().size());
        }

        out.remove(new Question("JavaQuestion", "JavaAnswer"));
        assertEquals(0, out.getAll().size());
    }

    @Test
    void shouldAddTenQuestionsAndRemoveItWithSizeCheck() {

        for (int i = 0; i < 10; i++) {
            out.add(new Question("JavaQuestion" + i, "JavaAnswer"));
            assertEquals(i + 1, out.getAll().size());
        }

        for (int i = 0; i < 10; i++) {
            out.remove(new Question("JavaQuestion" + i, "JavaAnswer"));
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
            out.add(new Question("JavaQuestion" + i, "JavaAnswer"));
            expectedSet.add(new Question("JavaQuestion" + i, "JavaAnswer"));
        }
        assertEquals(expectedSet, out.getAll());
    }
}
