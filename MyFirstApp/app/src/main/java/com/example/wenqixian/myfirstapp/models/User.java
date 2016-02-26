package com.example.wenqixian.myfirstapp.models;


/**
 * Created by wenqixian on 2/13/16.
 */
public class User {

    String fullname;
    String gtid;
    String email;
    String mobile;

    public User() {

    }
    public User(String fullname, String gtid, String email, String mobile) {
        this.fullname = fullname;
        this.gtid = gtid;
        this.email= email;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", gtid='" + gtid + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        this.gtid = gtid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}