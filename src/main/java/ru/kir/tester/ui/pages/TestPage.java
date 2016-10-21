package ru.kir.tester.ui.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import ru.kir.tester.common.DataHelper;
import ru.kir.tester.domain.Question;
import ru.kir.tester.ui.MainTester;

import java.util.ArrayList;
import java.util.List;

import static ru.kir.tester.common.Helper.makeAnswers;
import static ru.kir.tester.common.Helper.mix;

/**
 * Created by Kirill Zhitelev on 13.09.2016.
 */
public class TestPage {
    private List<Question> questions;
    private List<String[]> listAnswers = new ArrayList<>();
    private List<String> selectedAnswers = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<RadioButton> radioButtons;
    private Scene scene;
    private Button menu = new Button("В меню");
    private Button completedTest = new Button("Завершить тест");
    private Stage stage;
    private MainTester tester;
    private boolean completed;
    private Pagination pagination;
    private Label correct = new Label("Верно: ");
    private Label wrong = new Label("Неверно: ");
    private DataHelper dataHelper;
    private ImageView view = new ImageView(new Image("/galochka.png"));

    public TestPage(Stage stage, MainTester tester) {
        this.stage = stage;
        this.tester = tester;
        dataHelper = tester.getDataHelper();
        questions = new ArrayList<>(dataHelper.getQuestions());

        initPage();
        initListAnswers();
        iniSelectedAnswers();
    }

    private void initPage() {
        pagination = new Pagination(questions.size(), 0);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(param);
            }
        });
        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pagination, 10.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 10.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        anchor.getChildren().addAll(pagination);
        VBox box = new VBox(10);
        HBox hBoxButton = new HBox(130);
        HBox hBoxLabel = new HBox(100);
        hBoxButton.setAlignment(Pos.CENTER);
        hBoxLabel.setAlignment(Pos.CENTER);
        hBoxLabel.getChildren().addAll(correct, wrong);
        hBoxButton.getChildren().addAll(menu, completedTest);
        box.getChildren().addAll(hBoxButton, hBoxLabel, anchor);
        ScrollBar bar = new ScrollBar();
        bar.setOrientation(Orientation.VERTICAL);
        bar.setMax(1000);
        box.getChildren().add(bar);
        bar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                box.setLayoutY(newValue.doubleValue());
            }
        });

        scene = new Scene(box);

        addEventHandler();
    }

    private void addEventHandler() {
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(tester.getMainPage().getScene());
            }
        });

        completedTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int countCorrectAnswers = countCorrectAnswers(selectedAnswers, correctAnswers);
                correct.setText(correct.getText() + countCorrectAnswers);
                wrong.setText(wrong.getText() + (questions.size() - countCorrectAnswers));
                completedTest.setDisable(true);
                workWithButton(pagination.getCurrentPageIndex());
                completed = true;
            }
        });
    }

    private void initListAnswers() {
        for (Question question: questions) {
            String correctAnswer = question.getAnswer().getCorrectAnswer();
            correctAnswers.add(correctAnswer);
            String wrongAnswers = question.getAnswer().getWrongAnswers();
            listAnswers.add(mix(makeAnswers(wrongAnswers, correctAnswer)));
        }
    }

    private void iniSelectedAnswers() {
        for (int i = 0; i < questions.size(); i++) {
            selectedAnswers.add(null);
        }
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox(5);
        ToggleGroup group = new ToggleGroup();
        radioButtons = new ArrayList<>();
        int answersSize;

        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            TextArea textArea = new TextArea(questions.get(i).getQuestion());
            textArea.setFont(new Font(15));
            answersSize = listAnswers.get(page).length;

            textArea.setWrapText(true);
            textArea.setEditable(false);
            box.getChildren().add(textArea);

            for (int j = 0; j < answersSize; j++) {
                radioButtons.add(new RadioButton(getAnswer(i, j)));
                box.getChildren().add(radioButtons.get(j));
                radioButtons.get(j).setUserData(getAnswer(i, j));
            }
            setToggleGroup(group, radioButtons);

            group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    selectedAnswers.set(page, newValue.getUserData().toString());
                }
            });

            for (RadioButton radioButton: radioButtons) {
                if(radioButton.getText().equals(selectedAnswers.get(page))) {
                    radioButton.setSelected(true);
                }
            }

            if (completed) {
                workWithButton(page);
            }

        }
        return box;
    }

    private void workWithButton(int page) {
        for (RadioButton radioButton: radioButtons) {
            if(radioButton.getText().equals(correctAnswers.get(page))) {
                radioButton.setGraphic(view);
            }
            else
                radioButton.setDisable(true);
/*            if(!radioButton.getText().equals(correctAnswers.get(page)) && radioButton.isSelected()) {
                //radioButton.setText(radioButton.getText() + "   Верно: " + correctAnswers.get(page));
                radioButton.setGraphic(view);
            }*/
        }
    }

    private String getAnswer(int answerIndex , int index) {
        return listAnswers.get(answerIndex)[index];
    }

    private void setToggleGroup(ToggleGroup group, List<RadioButton> radioButtons) {
        for (RadioButton but: radioButtons) {
            but.setMaxWidth(450);
            but.setWrapText(true);
            but.setToggleGroup(group);
        }
    }

    public int countCorrectAnswers(List<String> selectedAnswers, List<String> correctAnswers) {
        int correctCount = 0;
        for (int i = 0; i < selectedAnswers.size(); i++) {
            if(selectedAnswers.get(i) != null) {
                if (selectedAnswers.get(i).equals(correctAnswers.get(i)))
                    correctCount++;
            }
        }

        return correctCount;
    }

    public Scene getScene() {
        return scene;
    }

    private int itemsPerPage() {
        return 1;
    }
}
