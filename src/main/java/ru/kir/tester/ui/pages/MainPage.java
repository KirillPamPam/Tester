package ru.kir.tester.ui.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kir.tester.common.DataHelper;
import ru.kir.tester.common.Helper;
import ru.kir.tester.common.TesterResources;
import ru.kir.tester.ui.MainTester;

/**
 * Created by Kirill Zhitelev on 18.09.2016.
 */
public class MainPage {
    private Scene scene;
    private Button constructor = new Button("Конструктор тестов");
    private Button test = new Button("Пройти тест");
    private ComboBox<String> comboBox;
    private Stage stage;
    private MainTester tester;
    private DataHelper dataHelper;

    public MainPage(Stage stage, MainTester tester) {
        this.stage = stage;
        this.tester = tester;

        dataHelper = tester.getDataHelper();

        comboBox = dataHelper.initComboBox(tester.getThemes());
        initPage();

        addEventHandler();
    }

    private void initPage() {
        Label theme = new Label("Тема:");
        BorderPane pane = new BorderPane();
        HBox box = new HBox(130);
        VBox otherBox = new VBox(15);
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(theme, comboBox);
        otherBox.getChildren().addAll(test, hBox);
        box.getChildren().addAll(constructor, otherBox);
        otherBox.setAlignment(Pos.CENTER);
        box.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        pane.setCenter(box);
        scene = new Scene(pane);
    }

    public Scene getScene() {
        return scene;
    }

    private void addEventHandler() {
        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (comboBox.getValue() == null) {
                    Helper.makeInformationWindow(Alert.AlertType.ERROR, TesterResources.CHOOSE_THEME, null, null);
                } else {
                    dataHelper.setQuestions(comboBox.getValue());
                    stage.setScene(new TestPage(stage, tester).getScene());
                }
            }
        });
        constructor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(new TestConstructor(tester, stage).getScene());
            }
        });
    }

}
