package com.example.accord.Models;

public class Order {
    public String sessionId;
    public String userId;
    public  String serviceProviderId;
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

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }
    public boolean getIsActive(){
        return  isActive;
    }
}
