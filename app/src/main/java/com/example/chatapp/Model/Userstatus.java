package com.example.chatapp.Model;

import java.util.ArrayList;

public class Userstatus {
    private String name,profileImage;
    private long lastupdated;
    private ArrayList<Normalstatus> stasues;
    public Userstatus(){

    }

    public Userstatus(String name, String profileImage, long lastupdated, ArrayList<Normalstatus> stasues) {
        this.name = name;
        this.profileImage = profileImage;
        this.lastupdated = lastupdated;
        this.stasues = stasues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(long lastupdated) {
        this.lastupdated = lastupdated;
    }

    public ArrayList<Normalstatus> getStasues() {
        return stasues;
    }

    public void setStasues(ArrayList<Normalstatus> stasues) {
        this.stasues = stasues;
    }
}
