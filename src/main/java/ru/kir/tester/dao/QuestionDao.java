package ru.kir.tester.dao;

import ru.kir.tester.domain.Question;
import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
public interface QuestionDao {
    List<Question> getAllQuestionsByTheme(Theme theme);
    void saveQuestion(Question question);
}
