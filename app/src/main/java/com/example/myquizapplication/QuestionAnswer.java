package com.example.myquizapplication;

import java.util.List;

public class QuestionAnswer {

    String question;
    String correrctAnswer;
    List<String> options;

    QuestionAnswer(String question, String correrctAnswer, List<String> options) {
        this.question = question;
        this.correrctAnswer = correrctAnswer;
        this.options = options;
    }


    public static String questions[] = {
            "Which company owns the android?",
            "Which one is not the programming language?"
    };

    public static String choices[][] = {
            {"Google", "Apple", "Nokia", "Samsung"},
            {"Java", "Kotlin", "Dart", "Notepad"},
    };

    public static String correctAnswers[] = {
            "Google",
            "Notepad"
    };
}
