package ru.kir.tester.dao;

import ru.kir.tester.domain.Answer;
import ru.kir.tester.domain.Theme;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
public interface AnswerDao {
    Answer getCorrectAnswer(String correctAnswer);
    void saveAnswer(Answer answer);
    void deleteAnswers(Theme theme);
}
