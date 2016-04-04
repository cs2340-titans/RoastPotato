package com.example.wenqixian.myfirstapp;

import com.example.wenqixian.myfirstapp.models.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * User Test
 *
 * @author Ziming He
 * @version 1.2
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
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
    }
    // endregion

    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01createDifferentUser() {
        User cs = new User(null, null, null, "Computer Science");
        User karma = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        User james = new User("James", null, null, null);
    }

    @Test(timeout = TIMEOUT)
    public void m02getterTest() {
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        Assert.assertEquals(test.getFullname(), "Karma");
        Assert.assertEquals(test.getGtid(), "123456789");
        Assert.assertEquals(test.getEmail(), "gg@gmail.com");
        Assert.assertEquals(test.getMajor(), "Industrial Engneering");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m03setFullnameNull() {
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        test.setFullname(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m04setGTIDNull() {
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        test.setGtid(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m05setMajorNull() {
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        test.setMajor(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void m06setEmailNull() {
        User test = new User("Karma", "123456789", "gg@gmail.com", "Industrial Engneering");
        test.setEmail(null);
    }

    @Test(timeout = TIMEOUT)
    public void m07toString() {
        User test = new User("Karma", null, "gg@gmail.com", "Industrial Engneering");
        Assert.assertEquals(test.toString(), "The user is missing one of the fields");
    }

}