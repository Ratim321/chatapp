package com.example.chatapp;

public class search {
    String username,userid;

    public String getUsername() {
        return username;
    }

    public search(String username, String userid) {
        this.username = username;
        this.userid = userid;
    }

    public search() {
        this.username = "hi";
        this.userid = "bye";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
