package ru.ldv236.exam.service;

import ru.ldv236.exam.domain.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);
}
