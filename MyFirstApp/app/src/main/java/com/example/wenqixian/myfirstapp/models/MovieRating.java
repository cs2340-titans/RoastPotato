package com.example.wenqixian.myfirstapp.models;

import android.support.annotation.NonNull;

/**
 * Created by wenqixian on 2/27/16.
 */
public class MovieRating {
    private String userMajor;
    private String movie;
    private float score;
    private float ranking;
    private String comment;

    // //Introducing the dummy constructor
    public MovieRating() {}


    public MovieRating(String userMajor, String movie, float score, float ranking, String comment) {
        this.userMajor = userMajor;
        this.movie = movie;
        this.score = score;
        this.ranking = ranking;
        this.comment = comment;
    }

    public String getComment() { return comment; }

    public float getScore() {
        return score;
    }

    public float getRanking() { return ranking; }

    public String getUserMajor() {
        return userMajor;
    }

    public String getMovie() { return movie; }

}
