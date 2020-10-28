package com.example.accord.Models;

import com.google.android.gms.maps.model.LatLng;

public class CustomUser {
    public String uid;
    public LatLng currentLocation;
    public CustomUser(){

    }

    public String getUid() {
        return uid;

    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }
}
