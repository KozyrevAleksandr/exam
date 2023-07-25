package ru.ldv236.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.repository.JavaQuestionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class JavaQuestionServiceTest {

    private JavaQuestionRepository questionRepository;
    private QuestionService out;

    @BeforeEach
    public void setUp() {
        this.questionRepository = Mockito.mock(JavaQuestionRepository.class);
        this.out = new JavaQuestionService(questionRepository);
    }

    static Stream<Arguments> provideParamsForTests() {
        return Stream.of(
                Arguments.of("Question1", "Answer1"),
                Arguments.of("Question2", "Answer2"),
                Arguments.of("Question3", "Answer3"),
                Arguments.of("Question4", "Answer4"),
                Arguments.of("Question5", "Answer5")
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForTests")
    void shouldAddAndRemoveNewQuestion(String question, String answer) {

        when(questionRepository.add(any())).thenReturn(new Question(question, answer));
        when(questionRepository.remove(any())).thenReturn(new Question(question, answer));

        Question actual = out.add(question, answer);
        Question expected = new Question(question, answer);
        assertEquals(expected, actual);

        Question actual2 = out.remove(question, answer);
        assertEquals(expected, actual2);
    }

    @Test
    void shouldReturnRandomQuestionOnSet() {

        when(questionRepository.getAll()).thenReturn(providedTestCollection(10));

        for (int i = 0; i < 10; i++) {
            assertTrue(providedTestCollection(10).contains(out.getRandomQuestion()));
        }
    }

    Collection<Question> providedTestCollection(int setSize) {
        Set<Question> result = new HashSet<>();
        for (int i = 0; i < setSize; i++) {
            result.add(new Question("Question" + i, "Answer" + i));
        }
        return result;
    }
}
