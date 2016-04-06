package com.example.wenqixian.myfirstapp;

import com.example.wenqixian.myfirstapp.models.Movie;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Movie  Test
 *
 * @author Liren Yu
 * @version 1.0
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserClassTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 8000;

    private static int successCounter = 0;
    // endregion

    // region [ SET UP ]

    @Before
    public void javaSort() {
        Movie test = new Movie( "123456789", "InsideOut");
    }
    // endregion

    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01createDifferentMovie() {
        Movie m1 = new Movie("id1", "m1");
	Movie m2 = new Movie("id2", "m2");
    }

    @Test(timeout = TIMEOUT)
    public void m02getterTest() {
        Movie test = new Movie("31415926", "utopia");
        Assert.assertEquals(test.getName(), "utopia");
	Assert.assertEquals(test.getId(), "31415926");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m03setIDNull() {
	Movie test = new Movie("12343324", "aBadMovie");
        test.setId(null);
    }
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m04setNameNull() {
	Movie test = new Movie("12343324", "aBadMovie");
        test.setName(null);
    }

}
