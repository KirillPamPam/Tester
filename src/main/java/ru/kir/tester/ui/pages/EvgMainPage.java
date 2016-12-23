package ru.kir.tester.ui.pages;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.kir.tester.common.DataHelper;
import ru.kir.tester.common.Helper;
import ru.kir.tester.common.TesterResources;
import ru.kir.tester.ui.MainTester;

import java.util.List;


/**
 * Created by Kirill Zhitelev on 22.12.2016.
 */
public class EvgMainPage {
    private Stage stage;
    private MainTester tester;
    private DataHelper dataHelper;
    private Scene scene;

    public EvgMainPage(Stage stage, MainTester tester) {
        this.stage = stage;
        this.tester = tester;

        dataHelper = tester.getDataHelper();

        initPage();
    }

    private void initPage() {
        BorderPane pane = new BorderPane();
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(stage.widthProperty());
        pane.setTop(menuBar);
        Label tester = new Label("Tester");
        tester.setFont(new Font(60));
        pane.setCenter(tester);
        Menu mainMenu = new Menu("Меню");
        MenuItem constructor = new MenuItem("Конструктор тестов");
        MenuItem exit = new MenuItem("Выход");
        Menu test = new Menu("Пройти тест");

        initThemes(test);

        mainMenu.getItems().addAll(constructor, test, new SeparatorMenuItem(), exit);
        menuBar.getMenus().addAll(mainMenu);

        addEvent(test, constructor);
        exit.setOnAction(event -> Platform.exit());

        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    private void initThemes(Menu menu) {
        dataHelper.initComboBox(tester.getThemes());
        List<String> themes = dataHelper.getThemes();

        themes.forEach(theme -> menu.getItems().addAll(new MenuItem(theme)));
    }

    private void addEvent(Menu test, MenuItem constructor) {
        test.getItems().forEach(menuItem -> menuItem.setOnAction(event -> {
            if (menuItem.getText() == null) {
                Helper.makeInformationWindow(Alert.AlertType.ERROR, TesterResources.CHOOSE_THEME, null, null);
            } else {
                dataHelper.setQuestions(menuItem.getText());
                stage.setScene(new TestPage(stage, tester).getScene());
            }
        }));

        constructor.setOnAction(event -> stage.setScene(new TestConstructor(tester, stage).getScene()));
    }
}
