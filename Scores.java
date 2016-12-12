package com.lindsaykroeger.mygame;

/**
 * Created by Lindsay on 11/29/2016.
 */

public class Scores {

    private String name;
    private int score;
    private int id;

    public Scores(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Scores(String name, int score, int id) {
        this.name = name;
        this.score = score;
        this.id = id;
    }

    public String toString() {
        return "Name: " + this.name + "\nScore: " + String.valueOf(score);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
