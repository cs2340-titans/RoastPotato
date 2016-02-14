package com.example.wenqixian.myfirstapp;


/**
 * Created by wenqixian on 2/13/16.
 */
public class User {

    private String fullname;
    private String gtid;
    private String email;
    private String mobile;

    public User(String fullname, String gtid, String email, String mobile) {
        this.fullname = fullname;
        this.gtid = gtid;
        this.email= email;
        this.mobile = mobile;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGtid() {
        return gtid;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

}