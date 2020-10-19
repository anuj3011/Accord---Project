package com.example.accord.Models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class Session {
    User user;
    ServiceProvider serviceProvider;
    public String sessionID;
    String payment;
    String service;
    boolean isActive;
    boolean isPayed;
    boolean isCompleted;
    Session(){

    }
    public static Session fromSnapshot(DocumentSnapshot snapshot){
        return snapshot.toObject(Session.class);// converts map data to object
    }
    User getUser(){
        return user;
    }
    ServiceProvider getServiceProvider(){
        return serviceProvider;
    }
    String getSessionID(){
        return  sessionID;
    }
    boolean checkisActive(){
        return isActive;
    }

}
