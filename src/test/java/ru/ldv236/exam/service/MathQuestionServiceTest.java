package ru.ldv236.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.repository.MathQuestionRepository;

import java.util.stream.Stream;

public class MathQuestionServiceTest {

    private MathQuestionRepository questionRepository;
    private QuestionService out;

    @BeforeEach
    public void setUp() {
        this.questionRepository = Mockito.mock(MathQuestionRepository.class);
        this.out = new MathQuestionService(questionRepository);
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
}
