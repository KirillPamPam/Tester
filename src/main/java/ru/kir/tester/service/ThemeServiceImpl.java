package ru.kir.tester.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.tester.dao.AnswerDao;
import ru.kir.tester.dao.ThemeDao;
import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 18.09.2016.
 */
@Service("themeService")
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeDao themeDao;
    @Autowired
    private AnswerDao answerDao;

    @Override
    @Transactional(readOnly = true)
    public Theme getTheme(String theme) {
        return themeDao.getTheme(theme);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theme> getAllThemes() {
        return themeDao.getAllThemes();
    }

    @Override
    @Transactional
    public void saveAll(Theme theme) {
        themeDao.saveAll(theme);
    }

    @Override
    @Transactional
    public void deleteAll(Theme theme) {
        answerDao.deleteAnswers(themeDao.getTheme(theme.getTheme()));
        themeDao.deleteAll(theme);
    }
}
