package com.example.kanjimemorycards;

public class ScoreDatabase {

    String Name,Score;
    public ScoreDatabase(String Name, String Score) {
        this.Name=Name;
        this.Score=Score;
    }
    public ScoreDatabase(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }
}
