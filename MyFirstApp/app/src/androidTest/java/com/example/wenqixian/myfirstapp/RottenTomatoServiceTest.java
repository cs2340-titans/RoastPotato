package com.example.wenqixian.myfirstapp;

import com.example.wenqixian.myfirstapp.services.RottenTomatoService;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * RottenTomatoService Test
 *
 * @author Zhen Liu
 * @version 1.2
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RottenTomatoServiceTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 8000;

    private static int successCounter = 0;
    private RottenTomatoService rts;
    // endregion

    // region [ SET UP ]

    @Before
    public void begin() {
        rts = RottenTomatoService.getInstance();
    }
    // endregion

    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01TestSearchQuery() {
        String url = rts.getSearchQuery("test_query");
        Assert.assertEquals("http://api.rottentomatoes.com/api/public/v1.0/movies.json?page_limit=25&page=1&q=test_query&apikey=yedukp76ffytfuy24zsqk7f5", url);
    }

    @Test(timeout = TIMEOUT)
    public void m02TestSearchQueryWithSpace() {
        String url = rts.getSearchQuery("test_query test_query2");
        Assert.assertEquals("http://api.rottentomatoes.com/api/public/v1.0/movies.json?page_limit=25&page=1&q=test_query%20test_query2&apikey=yedukp76ffytfuy24zsqk7f5", url);
    }

    @Test(timeout = TIMEOUT)
    public void m03TestSearchQueryWithSpace() {
        String url = rts.getSearchQuery("test_query  test_query2");
        Assert.assertEquals("http://api.rottentomatoes.com/api/public/v1.0/movies.json?page_limit=25&page=1&q=test_query%20%20test_query2&apikey=yedukp76ffytfuy24zsqk7f5", url);
    }

    @Test(timeout = TIMEOUT)
    public void m04TestRecentMovies() {
        String url = rts.getRecentMovies();
        Assert.assertEquals("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=yedukp76ffytfuy24zsqk7f5", url);
    }

    @Test(timeout = TIMEOUT)
    public void m05TestRecentDVDs() {
        String url = rts.getRecentDVDs();
        Assert.assertEquals("http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=yedukp76ffytfuy24zsqk7f5", url);
    }

}