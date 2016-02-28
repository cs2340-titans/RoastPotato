package com.example.wenqixian.myfirstapp.models;

/**
 * Created by andy on 2/19/16.
 */
public class Movie {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private String id;
    private String name;
}
