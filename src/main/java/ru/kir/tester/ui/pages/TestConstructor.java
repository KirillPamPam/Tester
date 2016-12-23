package ru.kir.tester.ui.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kir.tester.common.DataHelper;
import ru.kir.tester.common.TesterResources;
import ru.kir.tester.ui.MainTester;

import java.util.List;

import static ru.kir.tester.common.Helper.makeInformationWindow;
import static ru.kir.tester.common.Helper.makeWrongAnswersForBase;

/**
 * Created by Kirill Zhitelev on 25.09.2016.
 */
public class TestConstructor {
    private MainTester tester;
    private Stage stage;
    private Scene scene;
    private Label theme = new Label("Тема");
    private Label question = new Label("Вопрос");
    private Label correctAnswer = new Label("Правильный ответ");
    private Label wrongAnswer = new Label("Неправильные ответы");
    private ComboBox themeField;
    private TextArea questionArea = new TextArea();
    private TextField correctAnswerField = new TextField();
    //private TextField wrongAnswersField = new TextField();
    private Button addQuestion = new Button("Сохранить");
    private Button menu = new Button("В меню");
    private DataHelper dataHelper;
    private Button addAnswer = new Button("Добавить");
    private Button removeAnswer = new Button("Удалить");
    //private List<TextField> answers = new ArrayList<>();
    private VBox answersBox = new VBox(10);
    private TextArea wrongArea;
    private ImageView image = new ImageView(new Image("/question.png"));

    public TestConstructor(MainTester tester, Stage stage) {
        this.tester = tester;
        this.stage = stage;

        dataHelper = tester.getDataHelper();

        initPage();
        addEventHandler();
    }

    private void initPage() {
        themeField = dataHelper.initComboBox(tester.getThemes());
        themeField.setEditable(true);

        BorderPane borderPane = new BorderPane();
        VBox vMain = new VBox(15);

        HBox themes = new HBox(10, theme, themeField);
        themes.setAlignment(Pos.CENTER);

        questionArea.setMaxSize(350, 100);
        questionArea.setWrapText(true);
        HBox questions = new HBox(10, question, questionArea);
        questions.setAlignment(Pos.CENTER);

        correctAnswerField.setPrefWidth(250);
        HBox correct = new HBox(10, correctAnswer, correctAnswerField);
        correct.setAlignment(Pos.CENTER);

/*        wrongAnswersField.setMaxSize(300, 80);
        wrongAnswersField.setPrefWidth(200);
        removeAnswer.setDisable(true);
        HBox wrong = new HBox(5, wrongAnswer, wrongAnswersField, addAnswer, removeAnswer);
        wrong.setAlignment(Pos.CENTER);*/

        wrongArea = new TextArea();
        wrongArea.setMaxSize(330, 100);
        wrongAnswer.setWrapText(true);
        HBox wrong = new HBox(5, wrongAnswer, wrongArea, image);
        wrong.setAlignment(Pos.CENTER);

        answersBox.setAlignment(Pos.CENTER);
        vMain.setAlignment(Pos.CENTER);
        vMain.getChildren().addAll(menu, themes, questions, correct, wrong, answersBox, addQuestion);

        borderPane.setCenter(vMain);

        scene = new Scene(borderPane);
    }

    private void addEventHandler() {
        image.setOnMouseClicked(event ->
                makeInformationWindow(Alert.AlertType.INFORMATION, "Каждый новый ответ пишите на новой строке", null, null));

        addQuestion.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String theme = (String) themeField.getValue();
                String question = questionArea.getText();
                String correctAnswer = correctAnswerField.getText();
                String wrongAnswers = makeWrongAnswersForBase(wrongArea.getText());

                if(themeField.getValue() == null ||
                        theme.equals("") || question.equals("") || correctAnswer.equals("")
                        || wrongArea.getText().equals("")) {
                    makeInformationWindow(Alert.AlertType.ERROR, TesterResources.ENTER_ALL_FIELDS, null, null);
                    return;
                }

                if(wrongAnswers == null) {
                    makeInformationWindow(Alert.AlertType.ERROR, "Неправильных ответов должно быть не больше 5", null, null);
                    return;
                }

                dataHelper.addTheme(theme);
                dataHelper.save(theme, question, correctAnswer, wrongAnswers);
                questionArea.setText("");
                correctAnswerField.setText("");
                wrongArea.setText("");

                makeInformationWindow(Alert.AlertType.INFORMATION, TesterResources.SAVED, null, null);
            }
        });
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(new EvgMainPage(stage, tester).getScene());
            }
        });
/*        addAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answers.size() == 0) {
                    removeAnswer.setDisable(false);
                }

                TextField newAnswer = new TextField();
                newAnswer.setMaxWidth(250);
                answersBox.getChildren().add(newAnswer);
                answers.add(newAnswer);

                if(answers.size() == 4) {
                    addAnswer.setDisable(true);
                }
            }
        });
        removeAnswer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAnswer.setDisable(false);

                answersBox.getChildren().remove(answers.size() - 1);
                answers.remove(answers.size() - 1);

                if(answers.size() == 0) {
                    removeAnswer.setDisable(true);
                }
            }
        });*/
    }

    private boolean isFieldEmpty(List<TextField> answers) {
        for (TextField answer: answers) {
            if(answer.getText().equals(""))
                return true;
        }
        return false;
    }

    public Scene getScene() {
        return scene;
    }
}
