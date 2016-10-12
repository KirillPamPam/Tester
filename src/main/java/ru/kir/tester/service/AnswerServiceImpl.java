package ru.kir.tester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.tester.dao.AnswerDao;
import ru.kir.tester.domain.Answer;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerDao answerDao;

    @Override
    @Transactional(readOnly = true)
    public Answer getCorrectAnswer(String correctAnswer) {
        return answerDao.getCorrectAnswer(correctAnswer);
    }

    @Override
    @Transactional
    public void saveAnswer(Answer answer) {
        answerDao.saveAnswer(answer);
    }
}
