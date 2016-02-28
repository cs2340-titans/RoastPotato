package com.example.wenqixian.myfirstapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by andy on 2/6/16.
 */
public class MyFirstApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyFirstApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyFirstApp.context;
    }
}