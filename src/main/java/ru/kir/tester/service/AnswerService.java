package ru.kir.tester.service;

import ru.kir.tester.domain.Answer;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
public interface AnswerService {
    Answer getCorrectAnswer(String correctAnswer);
    void saveAnswer(Answer answer);
}
