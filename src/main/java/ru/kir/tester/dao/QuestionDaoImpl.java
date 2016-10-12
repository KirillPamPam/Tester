package ru.kir.tester.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.tester.domain.Question;
import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Question> getAllQuestionsByTheme(Theme theme) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Question.class).add(Restrictions.eq("theme", theme)).list();
    }

    @Override
    public void saveQuestion(Question question) {
        Session session = sessionFactory.getCurrentSession();
        session.save(question);
    }
}
