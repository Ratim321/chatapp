package com.example.chatapp.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Users {
    String profilepicture,username,mail,passoward,userid,lastmessege,status,freind,receiverequest,grantedrequest,storyimg,storyimgid,timestamp,mainidimage;
    ArrayList<String> numberoffreind;

    public String getMainidimage() {
        return mainidimage;
    }

    public void setMainidimage(String mainidimage) {
        this.mainidimage = mainidimage;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStoryimgid() {
        return storyimgid;
    }

    public void setStoryimgid(String storyimgid) {
        this.storyimgid = storyimgid;
    }

    public String getStoryimg() {
        return storyimg;
    }

    public void setStoryimg(String storyimg) {
        this.storyimg = storyimg;
    }

    public String getGrantedrequest() {
        return grantedrequest;
    }

    public void setGrantedrequest(String grantedrequest) {
        this.grantedrequest = grantedrequest;
    }

    public String getReceiverequest() {
        return receiverequest;
    }

    public void setReceiverequest(String receiverequest) {
        this.receiverequest = receiverequest;
    }

    public ArrayList<String> getNumberoffreind() {
        return numberoffreind;
    }

    public void setNumberoffreind(ArrayList<String> numberoffreind) {
        this.numberoffreind = numberoffreind;
    }

    public String getFreind() {
        return freind;
    }

    public void setFreind(String freind) {
        this.freind = freind;
    }

    public String getStatus() {
        return status;
    }

    public Users(String profilepicture, String username, String mail, String passoward, String userid, String lastmessege, String status) {
        this.profilepicture = profilepicture;
        this.username = username;
        this.mail = mail;
        this.passoward = passoward;
        this.userid = userid;
        this.lastmessege = lastmessege;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String profilepicture, String username, String mail, String passoward, String userid, String lastmessege) {
        this.profilepicture = profilepicture;
        this.username = username;
        this.mail = mail;
        this.passoward = passoward;
        this.userid = userid;
        this.lastmessege = lastmessege;
    }

      public Users(){}
          //sighnup;
            public Users( String username, String mail, String passoward) {

              this.username = username;
              this.mail = mail;
              this.passoward = passoward;

          }




    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassoward() {
        return passoward;
    }

    public void setPassoward(String passoward) {
        this.passoward = passoward;
    }

    public String getUserid() {
        return userid;
    }



    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessege() {
        return lastmessege;
    }

    public void setLastmessege(String lastmessege) {
        this.lastmessege = lastmessege;
    }
}
