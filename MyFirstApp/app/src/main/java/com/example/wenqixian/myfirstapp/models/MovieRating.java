package com.example.wenqixian.myfirstapp.models;

/**
 * Created by wenqixian on 2/27/16.
 */
public class MovieRating {
    public String userID;
    public String movieID;
    public float score;
    public String comment;

    // //Introducing the dummy constructor
    public MovieRating() {}


    public MovieRating(String userID, String movieID, float score, String comment) {
        this.userID = userID;
        this.movieID = movieID;
        this.score = score;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public float getScore() {
        return score;
    }

}
