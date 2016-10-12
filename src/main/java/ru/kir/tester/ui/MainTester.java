package ru.kir.tester.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import ru.kir.tester.common.DataHelper;
import ru.kir.tester.domain.Theme;
import ru.kir.tester.service.QuestionService;
import ru.kir.tester.service.ThemeService;
import ru.kir.tester.ui.pages.MainPage;

import java.util.List;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
public class MainTester extends Application {
    private QuestionService questionService;
    private ThemeService themeService;
    private ApplicationContext context;
    private MainPage mainPage;
    private List<Theme> themes;
    private DataHelper dataHelper;

    private void initApp() {
        context = new GenericXmlApplicationContext("/spring/app-context-config.xml");
        questionService = context.getBean("questionService", QuestionService.class);
        themeService = context.getBean("themeService", ThemeService.class);

        dataHelper = new DataHelper(context);

        themes = themeService.getAllThemes();
    }

    @Override
    public void start(Stage stage) throws Exception {
        initApp();

        mainPage = new MainPage(stage, this);

        stage.setScene(mainPage.getScene());

        stage.setWidth(500);
        stage.setHeight(460);
        stage.setTitle("Tester");
        stage.setResizable(false);
        stage.show();
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public DataHelper getDataHelper() {
        return dataHelper;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
