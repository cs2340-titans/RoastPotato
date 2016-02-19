package com.example.wenqixian.myfirstapp;

import android.content.Context;

import com.firebase.client.Firebase;
import com.example.wenqixian.myfirstapp.R;

/**
 * Created by andy on 2/6/16.
 */
public class FirebaseSingleton {
    private static FirebaseSingleton ourInstance = new FirebaseSingleton();

    private Firebase ref;

    public static FirebaseSingleton getInstance() {
        return ourInstance;
    }

    private FirebaseSingleton() {
        Firebase.setAndroidContext(MyFirstApp.getAppContext());
        ref = new Firebase(MyFirstApp.getAppContext().getResources().getString(R.string.FirebaseURL));
    }

    public Firebase ref() {
        return ref;
    }
}
