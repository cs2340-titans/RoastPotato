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

    //Introducing the dummy constructor
    public MovieRating() {}


    public MovieRating(String userMajor, String movie, float score, float ranking, String comment) {
        this.userMajor = userMajor;
        this.movie = movie;
        this.score = score;
        this.comment = comment;
    }

    public String getComment() { return comment; }

    public float getScore() { return score; }

    public float getRanking() {
        return ranking;
    }

    public String getUserMajor() {
        return userMajor;
    }

    public String getMovie() { return movie; }

    public void setUserMajor(String userMajor) {
        if (userMajor == null) {
            throw new IllegalArgumentException("user's major cannot be null");
        }
        this.userMajor = userMajor;
    }

    public void setMovieName(String movie) {
        if (movie == null) {
            throw new IllegalArgumentException("movie's name cannot be null.");
        }
        this.movie = movie;
    }

    public void setScore(float score) {
        if (score > 5) {
            throw new IllegalArgumentException("score cannot be above 5.");
        }
        this.score = score;
    }

    public void setRanking(float ranking) {
        if (ranking < 0) {
            throw new IllegalArgumentException("ranking cannot be negative.");
        }
        this.ranking = ranking;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
