package com.example.chatapp.Model;

public class Normalstatus {
    private String imageurl;
    private long timstamp;
   public Normalstatus(){

   }
    public String getImageurl() {
        return imageurl;
    }

    public Normalstatus(String imageurl, long timstamp) {
        this.imageurl = imageurl;
        this.timstamp = timstamp;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getTimstamp() {
        return timstamp;
    }

    public void setTimstamp(long timstamp) {
        this.timstamp = timstamp;
    }
}
