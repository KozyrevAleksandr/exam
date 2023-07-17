package ru.ldv236.exam.repository;

import org.springframework.stereotype.Repository;
import ru.ldv236.exam.domain.Question;
import ru.ldv236.exam.exception.QuestionNotFoundException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JavaQuestionRepository implements QuestionRepository {

    private Set<Question> questions = new HashSet<>();

    @PostConstruct
    private void Init() {
        for (int i = 0; i < 10; i++) {
            questions.add(new Question("JavaQuestion" + i, "JavaAnswer" + i));
        }
    }

    @Override
    public Question add(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (questions.contains(question)) {
            questions.remove(question);
            return question;
        }
        throw new QuestionNotFoundException("Question doesn't exist");
    }

    @Override
    public Collection<Question> getAll() {
        return questions;
    }

}
