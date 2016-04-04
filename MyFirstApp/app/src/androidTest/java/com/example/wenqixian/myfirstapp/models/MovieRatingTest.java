package com.example.wenqixian.myfirstapp.models;

import com.example.wenqixian.myfirstapp.models.MovieRating;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
/**
 * Movie Rating Test
 *
 * @author Wenqi Xian
 * @version 1.0
 */

public class MovieRatingTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 8000;

    // endregion

    // region [ SET UP ]

    @Before
    public void javaSort() {
        MovieRating test = new MovieRating("Computer Science", "Panda", (float)4.5, (float)1.50, "23333");
    }
    // endregion

    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01createDifferentRating() {
        MovieRating a = new MovieRating("Computer Science", "Panda", (float)4.5, (float)1.50, "23333");
        MovieRating b = new MovieRating("Computer Engineering", "China town", (float)3.5, (float)2.50, "23333");
        MovieRating c = new MovieRating("Industrial Engineering", "Zootopia", (float)5.0, (float)1.0, "23333");
    }

    @Test(timeout = TIMEOUT)
    public void m02getterTest() {
        MovieRating a = new MovieRating("Computer Science", "Panda", (float)4.5, (float)1.50, "23333");
        Assert.assertEquals(a.getUserMajor(), "Computer Science");
        Assert.assertEquals(a.getMovie(), "Panda");
        Assert.assertEquals(a.getScore(), 4.5);
        Assert.assertEquals(a.getRanking(), 1.50);
        Assert.assertEquals(a.getComment(), "23333");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m03setUserMajorNull() {
        MovieRating b = new MovieRating("Computer Engineering", "China town", (float)3.5, (float)2.50, "23333");
        b.setUserMajor(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m04setMovieNameNull() {
        MovieRating b = new MovieRating("Computer Engineering", "China town", (float)3.5, (float)2.50, "23333");
        b.setMovieName(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m05setScoreError() {
        MovieRating b = new MovieRating("Computer Engineering", "China town", (float)3.5, (float)2.50, "23333");
        b.setScore(7);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m06setRankingError() {
        MovieRating b = new MovieRating("Computer Engineering", "China town", (float)3.5, (float)2.50, "23333");
        b.setRanking(-1);
    }

}