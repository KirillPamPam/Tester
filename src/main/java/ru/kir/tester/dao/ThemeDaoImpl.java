package ru.kir.tester.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 18.09.2016.
 */
@Repository("themeDao")
public class ThemeDaoImpl implements ThemeDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Theme getTheme(String theme) {
        Session session = sessionFactory.getCurrentSession();
        return (Theme) session.createCriteria(Theme.class).add(Restrictions.eq("theme", theme)).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Theme> getAllThemes() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Theme.class).list();
    }

    @Override
    public void saveAll(Theme theme) {
        Session session = sessionFactory.getCurrentSession();
        session.save(theme);
    }

    @Override
    public void deleteAll(Theme theme) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Theme t where t.theme = :theme");
        query.setParameter("theme", theme.getTheme());
        query.executeUpdate();
    }
}
