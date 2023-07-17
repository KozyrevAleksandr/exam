package ru.ldv236.exam.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.repository.QuestionRepository;

import java.util.Collection;
import java.util.Random;

@Service
public class JavaQuestionService implements QuestionService {

    QuestionRepository questionRepository;
    Random random = new Random();

    public JavaQuestionService(@Qualifier("javaQuestionRepository") QuestionRepository questionRepository) {
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
        Object[] array = questionRepository.getAll().toArray();
        int randomIndex = random.nextInt(array.length);
        Object randomQuestion = array[randomIndex];
        return (Question) randomQuestion;
    }
}
