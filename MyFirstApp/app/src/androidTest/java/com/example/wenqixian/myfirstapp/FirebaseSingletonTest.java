package com.example.wenqixian.myfirstapp;

import com.example.wenqixian.myfirstapp.models.User;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.Firebase;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by andy on 4/4/16.
 */
/**
 * Firebase Singleton Test
 *
 * @author Dezhi Fang
 * @version 1.2
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirebaseSingletonTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 8000;

    private static int successCounter = 0;
    // endregion

    // region [ SET UP ]


    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01createReference() {
        Firebase ref = new Firebase("https://roast-potato.firebaseio.com/");
    }

    @Test(timeout = TIMEOUT)
    public void m02createSingleton() {
        FirebaseSingleton refIns = FirebaseSingleton.getInstance();
    }

    @Test(timeout = TIMEOUT)
    public void m03getSingleton() {
        Firebase ref = FirebaseSingleton.getInstance().ref();
    }

    @Test(timeout = TIMEOUT)
    public void m04getUniqueSingleton() {
        Firebase ref = FirebaseSingleton.getInstance().ref();
        Firebase ref1 = new Firebase("https://roast-potato.firebaseio.com/");
        assert(ref != ref1);
    }

    @Test(timeout = TIMEOUT)
    public void m05getSingletonEqn() {
        Firebase ref = FirebaseSingleton.getInstance().ref();
        Firebase ref1 = FirebaseSingleton.getInstance().ref();
        assert(ref == ref1);
    }

}
