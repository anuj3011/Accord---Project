package com.example.accord.Models;

public class Order {
    public String sessionID;
    public String userId;
    public  String serviceProviderID;
    public  String completedOnDate;
    public  boolean isActive;
    String serviceName;
    String serviceProviderName;
    public Order(){

    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCompletedOnDate() {
        return completedOnDate;
    }

    public String getServiceProviderID() {
        return serviceProviderID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUserId() {
        return userId;
    }
    public boolean getIsActive(){
        return  isActive;
    }
}
