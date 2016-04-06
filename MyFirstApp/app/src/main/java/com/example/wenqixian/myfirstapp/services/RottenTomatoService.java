package com.example.wenqixian.myfirstapp.services;

import android.content.Context;
import android.net.Uri;

import com.example.wenqixian.myfirstapp.MyFirstApp;
import com.example.wenqixian.myfirstapp.R;

/**
 * Created by andy on 2/25/16.
 */
public class RottenTomatoService {
    private static RottenTomatoService ourService = new RottenTomatoService();

    private Context ctx;

    public static RottenTomatoService getInstance() {
        return ourService;
    }

    private RottenTomatoService() {
        ctx = MyFirstApp.getAppContext();
    }

    public String getSearchQuery(String query) {
        String res = "";
        res += ctx.getResources().getString(R.string.api_endpoint);
        res += ctx.getResources().getString(R.string.search_partial);
        res += Uri.encode(query);
        res += "&" + ctx.getResources().getString(R.string.rotten_tomato_key);
        return res;
    }

    public String getRecentMovies() {
        String res = "";
        res += ctx.getResources().getString(R.string.api_endpoint);
        res += ctx.getResources().getString(R.string.recent_movie_partial);
        res += "?" + ctx.getResources().getString(R.string.rotten_tomato_key);
        return res;
    }

    public String getRecentDVDs() {
        String res = "";
        res += ctx.getResources().getString(R.string.api_endpoint);
        res += ctx.getResources().getString(R.string.recent_dvd_partial);
        res += "?" + ctx.getResources().getString(R.string.rotten_tomato_key);
        return res;
    }
}
