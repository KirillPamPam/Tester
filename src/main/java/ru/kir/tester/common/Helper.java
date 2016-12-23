package ru.kir.tester.common;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Random;

/**
 * Created by Kirill Zhitelev on 11.09.2016.
 */
public class Helper {
    private static Random random = new Random();

    public static String[] makeAnswers(String wrongAnswers, String correctAnswer) {
        String newWrongAnswers = wrongAnswers.concat("\\," + correctAnswer);
        return newWrongAnswers.split("\\\\,");
    }

    public static String[] mix(String[] answers) {
        for (int i = 0; i < answers.length; i++) {
            int randIndex = random.nextInt(answers.length);
            String answer = answers[randIndex];
            answers[randIndex] = answers[i];
            answers[i] = answer;
        }

        return answers;
    }

    public static void makeInformationWindow(Alert.AlertType type, String contentText, String headerText, String title) {
        Alert alert = new Alert(type);
        alert.setContentText(contentText);
        alert.setHeaderText(headerText);
        alert.setTitle(title);
        alert.showAndWait();
    }


    public static String makeWrongAnswersForBase(String wrongAnswer, List<TextField> answers) {
        StringBuilder builder = new StringBuilder();
        for (TextField answer: answers) {
            builder.append(answer.getText()).append("\\,");
        }
        builder.append(wrongAnswer);

        return String.valueOf(builder);
    }

    public static String makeWrongAnswersForBase(String wrongAnswers) {
        StringBuilder builder = new StringBuilder();
        String[] answers = wrongAnswers.split("\n");

        for (int i = 1; i < answers.length; i++) {
            builder.append(answers[i]).append("\\,");
        }

        builder.append(answers[0]);

        System.out.println(builder);

        if(answers.length > 5)
            return null;

        return String.valueOf(builder);
    }
}
