package ru.kir.tester.service;

import ru.kir.tester.domain.Theme;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 18.09.2016.
 */
public interface ThemeService {
    Theme getTheme(String theme);
    List<Theme> getAllThemes();
    void saveAll(Theme theme);
    void deleteAll(Theme theme);
}
