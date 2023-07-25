package ru.ldv236.exam.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.exception.TooManyQuestionsRequestedException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    Random random = new Random();
    private final QuestionService javaQuestionService;
    private final QuestionService mathQuestionService;
    private Question nextRandomQuestion;

    public ExaminerServiceImpl(@Qualifier("javaQuestionService") QuestionService javaQuestionService,
                               @Qualifier("mathQuestionService") QuestionService mathQuestionService) {
        this.javaQuestionService = javaQuestionService;
        this.mathQuestionService = mathQuestionService;
    }

    @Override
    public Collection<Question> getQuestions(int amount) {

        int javaQuestionsCount = javaQuestionService.getAll().size();
        int mathQuestionsCount = mathQuestionService.getAll().size();
        int commonQuestionCount = javaQuestionsCount + mathQuestionsCount;

        if (amount > commonQuestionCount) {
            throw new TooManyQuestionsRequestedException("Not enough questions access");
        }

        Set<Question> result = new HashSet<>();

        while (result.size() < amount) {
            int subjectSelect = random.nextInt(2);

            //switch for easy add new subjects in future
            nextRandomQuestion = switch(subjectSelect) {
                case 0 -> javaQuestionService.getRandomQuestion();
                case 1 -> mathQuestionService.getRandomQuestion();
                // cases must cover all dependency services
                // (look bound arg in .nextInt() int subjectSelect initialize)
                default -> null;
            };

            if (!result.contains(nextRandomQuestion)) {
                result.add(nextRandomQuestion);
            }
        }
        return result;
    }
}

