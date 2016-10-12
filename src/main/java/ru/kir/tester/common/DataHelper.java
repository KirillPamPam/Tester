package ru.kir.tester.common;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.springframework.context.ApplicationContext;
import ru.kir.tester.domain.Answer;
import ru.kir.tester.domain.Question;
import ru.kir.tester.domain.Theme;
import ru.kir.tester.service.AnswerService;
import ru.kir.tester.service.QuestionService;
import ru.kir.tester.service.ThemeService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kirill Zhitelev on 29.09.2016.
 */
public class DataHelper {
    private ThemeService themeService;
    private AnswerService answerService;
    private QuestionService questionService;
    private ObservableList<String> themes = FXCollections.observableArrayList();
    private List<Question> questions;

    public DataHelper(ApplicationContext context) {
        themeService = context.getBean("themeService", ThemeService.class);
        answerService = context.getBean("answerService", AnswerService.class);
        questionService = context.getBean("questionService", QuestionService.class);
    }

    public ComboBox<String> initComboBox(List<Theme> listThemes) {
        if(themes.size() == 0) {
            for (Theme theme : listThemes) {
                themes.add(theme.getTheme());
            }
        }
        return new ComboBox<>(themes);
    }

    public void addTheme(String theme) {
        Theme baseTheme = themeService.getTheme(theme);
        if(baseTheme == null)
            themes.add(theme);
    }

    public void setQuestions(String theme) {
        questions = new ArrayList<>(questionService.getAllQuestionsByTheme(themeService.getTheme(theme)));
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void save(String theme, String question, String correctAnswer, String wrongAnswers) {
        Theme baseTheme = themeService.getTheme(theme);

        Question baseQuestion = new Question();
        baseQuestion.setQuestion(question);

        Answer answer = new Answer();
        answer.setCorrectAnswer(correctAnswer);
        answer.setWrongAnswers(wrongAnswers);

        baseQuestion.setAnswer(answer);

        Set<Question> questions = new HashSet<>();
        questions.add(baseQuestion);

        if(baseTheme == null) {
            baseTheme = new Theme();
            baseTheme.setTheme(theme);

            baseQuestion.setTheme(baseTheme);
            baseTheme.setQuestions(questions);

            themeService.saveAll(baseTheme);
        }
        else {
            baseQuestion.setTheme(baseTheme);
            baseTheme.setQuestions(questions);

            questionService.saveQuestion(baseQuestion);
        }



    }
}
