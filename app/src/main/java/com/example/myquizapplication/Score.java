package com.example.myquizapplication;

import java.time.LocalDateTime;

public class Score {

    public String name;
    public int score;
    public LocalDateTime dateTime;

    Score(String name, int score, LocalDateTime dateTime) {
        this.name = name;
        this.score = score;
        this.dateTime = dateTime;
    }
}
