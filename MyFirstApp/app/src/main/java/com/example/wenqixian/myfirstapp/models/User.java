package com.example.wenqixian.myfirstapp.models;


/**
 * Created by wenqixian on 2/13/16.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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
        if (fullname == null || gtid == null || email == null || major == null) {
            return "The user is missing one of the fields";
        }
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
        if (fullname == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.fullname = fullname;
    }

    public String getGtid() {
        return gtid;
    }

    public void setGtid(String gtid) {
        if (gtid == null) {
            throw new IllegalArgumentException("gtid cannot be null");
        }
        this.gtid = gtid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("email cannot be null");
        }
        this.email = email;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        if (major == null) {
            throw new IllegalArgumentException("major cannot be null");
        }
        this.major = major;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}