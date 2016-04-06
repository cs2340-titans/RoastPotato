package com.example.wenqixian.myfirstapp.models;

/**
 * Created by andy on 2/19/16.
 */
public class Movie {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id==null) {
            throw new IllegalArgumentException("gtid cannot be null");
        }
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name==null) {
            throw new IllegalArgumentException("gtid cannot be null");
        }
        this.name = name;
    }
    public Movie(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Movie getMovieById() {
        return null;
    }
}
