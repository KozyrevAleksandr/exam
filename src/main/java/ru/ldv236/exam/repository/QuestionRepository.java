package ru.ldv236.exam.repository;

import ru.ldv236.exam.domain.Question;

import java.util.Collection;

public interface QuestionRepository {

    Question add(Question question);
    Question remove(Question question);
    Collection<Question> getAll();
}
