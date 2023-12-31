package ru.ldv236.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class JavaQuestionService implements QuestionService {

    QuestionRepository questionRepository;
    Random random;

    //конструктор для работы спринга с аннотацией
    @Autowired
    public JavaQuestionService(@Qualifier("javaQuestionRepository") QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        random = new Random();
    }

    //конструктор для тестов (чтобы мокать рандом)
    public JavaQuestionService(@Qualifier("javaQuestionRepository") QuestionRepository questionRepository, Random random) {
        this.questionRepository = questionRepository;
        this.random = random;
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

//        List<Question> questions = new ArrayList<>()(questionRepository.getAll());
//        int randomIndex = random.nextInt(questions.size());
//        return  questions.get(randomIndex);

    }
}
