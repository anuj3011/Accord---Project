package com.example.accord.Models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class Session {
    public String userID;
    public String serviceProviderID;
    public String sessionID;
    public boolean isSearchStarted;
    public  boolean isAccepted;
    String payment;
    String service;
    public boolean isActive;
    boolean isPayed;
    public boolean isCompleted;
    public Session(){

    }
    public static Session fromSnapshot(DocumentSnapshot snapshot){
        return snapshot.toObject(Session.class);// converts map data to object
    }
    String getUser(){
        return userID;
    }
    String getServiceProvider(){
        return serviceProviderID;
    }
    String getSessionID(){
        return  sessionID;
    }
    boolean checkisActive(){
        return isActive;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public boolean isSearchStarted() {
        return isSearchStarted;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
