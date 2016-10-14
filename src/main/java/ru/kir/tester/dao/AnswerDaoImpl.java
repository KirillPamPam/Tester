package ru.kir.tester.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.tester.domain.Answer;
import ru.kir.tester.domain.Theme;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
@Repository("answerDao")
public class AnswerDaoImpl implements AnswerDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Answer getCorrectAnswer(String correctAnswer) {
        Session session = sessionFactory.getCurrentSession();
        return (Answer) session.createCriteria(Answer.class).add(Restrictions.eq("correctAnswer", correctAnswer)).uniqueResult();
    }

    @Override
    public void saveAnswer(Answer answer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(answer);
    }

    @Override
    public void deleteAnswers(Theme theme) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Answer a where a.answerId in " +
                "(select q.answer.answerId from Question q where q.theme.id=:id)");
        query.setParameter("id", theme.getThemeId());
        query.executeUpdate();
        System.out.println(theme.getThemeId());
    }
}
