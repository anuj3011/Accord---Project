package com.example.accord.Models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class Session {
    public String userID;
    public String serviceProviderID;
    public String sessionID;
    String payment;
    String service;
    boolean isActive;
    boolean isPayed;
    boolean isCompleted;
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

}
