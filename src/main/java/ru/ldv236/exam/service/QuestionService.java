package ru.ldv236.exam.service;

import ru.ldv236.exam.domain.Question;

import java.util.Collection;

public interface QuestionService {

    Question add(String question, String answer);
    Question remove(String question, String answer);
    Collection<Question> getAll();
    Question getRandomQuestion();
}
