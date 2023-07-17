package ru.ldv236.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.exception.TooManyQuestionsRequestedException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ExaminerServiceTest {

    private JavaQuestionService javaQuestionService;
    private MathQuestionService mathQuestionService;
    private ExaminerService out;

    @BeforeEach
    public void setUp() {
        this.javaQuestionService = Mockito.mock(JavaQuestionService.class);
        this.mathQuestionService = Mockito.mock(MathQuestionService.class);
        this.out = new ExaminerServiceImpl(javaQuestionService, mathQuestionService);
    }

    Collection<Question> providedJavaCollection(int setSize) {
        Set<Question> result = new HashSet<>();
        for (int i = 0; i < setSize; i++) {
            result.add(new Question("JavaQuestion" + i, "JavaAnswer" + i));
        }
        return result;
    }

    Collection<Question> providedMathCollection(int setSize) {
        Set<Question> result = new HashSet<>();
        for (int i = 0; i < setSize; i++) {
            result.add(new Question("MathQuestion" + i, "MathAnswer" + i));
        }
        return result;
    }

    @Test
    void shouldThrowToMuchQuestionsRequestedException() {
        when(javaQuestionService.getAll()).thenReturn(providedJavaCollection(10));
        when(mathQuestionService.getAll()).thenReturn(providedMathCollection(10));
        assertThrows(TooManyQuestionsRequestedException.class,
                () -> out.getQuestions(21));
    }

    @Test
    void shouldGetRandomQuestion() {

        when(javaQuestionService.getAll()).thenReturn(providedJavaCollection(10));
        when(mathQuestionService.getAll()).thenReturn(providedMathCollection(10));

        when(javaQuestionService.getRandomQuestion())
                .thenReturn(new Question("JavaQuestion1", "JavaAnswer1"))
                .thenReturn(new Question("JavaQuestion2", "JavaAnswer2"));

        when(mathQuestionService.getRandomQuestion())
                .thenReturn(new Question("MathQuestion3", "MathAnswer3"))
                .thenReturn(new Question("MathQuestion4", "MathAnswer4"));

        Collection<Question> actual = out.getQuestions(4);
        Collection<Question> expected = providedJavaCollection(10);
                             expected.addAll(providedMathCollection(10));

        for (Question q : actual) {
            assertTrue(expected.contains(q));
        }
    }
}
