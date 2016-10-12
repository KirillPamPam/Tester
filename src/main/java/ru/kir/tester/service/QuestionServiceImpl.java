package ru.kir.tester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.tester.dao.QuestionDao;
import ru.kir.tester.domain.Question;
import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Question> getAllQuestionsByTheme(Theme theme) {
        return questionDao.getAllQuestionsByTheme(theme);
    }

    @Override
    @Transactional
    public void saveQuestion(Question question) {
        questionDao.saveQuestion(question);
    }
}
