package com.example.wenqixian.myfirstapp.models;


/**
 * Created by wenqixian on 2/13/16.
 */
public class User {

    String fullname;
    String gtid;
    String email;
    String major;
    String status;

    public User() {

    }
    public User(String fullname, String gtid, String email, String major, String status) {
        this.fullname = fullname;
        this.gtid = gtid;
        this.email= email;
        this.major = major;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullname='" + fullname + '\'' +
                ", gtid='" + gtid + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                ", status" + status + '\'' +
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

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}