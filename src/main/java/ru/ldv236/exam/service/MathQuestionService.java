package ru.ldv236.exam.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.repository.QuestionRepository;

import java.util.Collection;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService {

    QuestionRepository questionRepository;
    Random random = new Random();

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        return  questionRepository.add(newQuestion);
    }

    @Override
    public Question remove(String question, String answer) {
        Question removeQuestion = new Question(question, answer);
        return questionRepository.remove(removeQuestion);
    }

    @Override
    public Collection<Question> getAll() {
        return questionRepository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        //not use because generate math question for "mastermind" task
        if (false) {
            Object[] array = questionRepository.getAll().toArray();
            int randomIndex = random.nextInt(array.length);
            Object randomQuestion = array[randomIndex];
            return (Question) randomQuestion;
        }
        return generateMathQuestion();
    }

    private Question generateMathQuestion() {
        String question;
        String answer;

        int topNumber = 10;
        int num1 = random.nextInt(topNumber);
        int num2 = random.nextInt(topNumber);
        int actionSelect = random.nextInt(4);

        switch (actionSelect) {
            case 0 -> {
                question = String.format("%d + %d = ", num1, num2);
                answer = String.valueOf(num1 + num2);
            }
            case 1 -> {
                question = String.format("%d - %d = ", num1, num2);
                answer = String.valueOf(num1 - num2);
            }
            case 2 -> {
                question = String.format("%d * %d = ", num1, num2);
                answer = String.valueOf(num1 * num2);
            }
            default -> {
                question = String.format("%d / %d = ", (num1 * num2), num2);
                answer = String.valueOf(num1);
            }
        };
        return new Question(question, answer);
    }
}
