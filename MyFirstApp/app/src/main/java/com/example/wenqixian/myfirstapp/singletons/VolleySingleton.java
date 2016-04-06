package com.example.wenqixian.myfirstapp.singletons;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.wenqixian.myfirstapp.MyFirstApp;

/**
 * Created by andy on 2/19/16.
 */
public class VolleySingleton {
    private static VolleySingleton ourInstance = new VolleySingleton();

    public static VolleySingleton getInstance() {
        return ourInstance;
    }

    private RequestQueue queue;

    private VolleySingleton() {
        queue = Volley.newRequestQueue(MyFirstApp.getAppContext());
    }

    public RequestQueue queue() {
        return queue;
    }
}
