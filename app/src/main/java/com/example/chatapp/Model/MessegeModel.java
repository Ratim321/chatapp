package com.example.chatapp.Model;

import android.widget.ImageView;

public class MessegeModel {
  String uid,messege;
  Long  timestamp;
  String timetable,messegeid;

  public String getMessegeid() {
    return messegeid;
  }

  public void setMessegeid(String messegeid) {
    this.messegeid = messegeid;
  }

  public MessegeModel(String uid, String messege, Long timestamp, String timetable, String receveroom, String senderoom) {
    this.uid = uid;
    this.messege = messege;
    this.timestamp = timestamp;
    this.timetable = timetable;
    this.receveroom = receveroom;
    this.senderoom = senderoom;

  }

  public String getReceveroom() {
    return receveroom;
  }

  public void setReceveroom(String receveroom) {
    this.receveroom = receveroom;
  }

  String receveroom;

  public String getSenderoom() {
    return senderoom;
  }

  public MessegeModel(String uid, String messege, Long timestamp, String timetable, String senderoom) {
    this.uid = uid;
    this.messege = messege;
    this.timestamp = timestamp;
    this.timetable = timetable;
    this.senderoom = senderoom;

  }

  public void setSenderoom(String senderoom) {
    this.senderoom = senderoom;
  }

  String senderoom;

  public MessegeModel(String uid, String messege, Long timestamp, String timetable, ImageView fellings,String senderoom) {
    this.uid = uid;
    this.messege = messege;
    this.timestamp = timestamp;
    this.timetable = timetable;
    this.fellings = fellings;
    this.senderoom=senderoom;
  }

  public ImageView getFellings() {
    return fellings;
  }

  public void setFellings(ImageView fellings) {
    this.fellings = fellings;
  }

  ImageView fellings;

  public String getTimetable() {
    return timetable;
  }

  public void setTimetable(String timetable) {
    this.timetable = timetable;
  }

  public MessegeModel(String uid, String messege, Long timestamp, String timetable) {
    this.uid = uid;
    this.messege = messege;
    this.timestamp = timestamp;
    this.timetable = timetable;
  }

  public String getId() {
    return uid;
  }

  public void setId(String uid) {
    this.uid = uid;
  }

  public String getMessege() {
    return messege;
  }

  public MessegeModel(String uid, String messege) {
    this.uid = uid;
    this.messege = messege;
  }

  public MessegeModel() {

  }

  public void setMessege(String messege) {
    this.messege = messege;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public MessegeModel(String uid, String messege, Long timestamp) {
    this.uid = uid;
    this.messege = messege;
    this.timestamp = timestamp;
  }
}
