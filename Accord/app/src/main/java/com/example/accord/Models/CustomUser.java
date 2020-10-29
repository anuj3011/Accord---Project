package com.example.accord.Models;

import com.google.android.gms.maps.model.LatLng;

public class CustomUser {
    public String uid;
    public CustomLatLng currentLocation;
    public CustomUser(){

    }

    public String getUid() {
        return uid;

    }

    public CustomLatLng getCurrentLocation() {
        return currentLocation;
    }
}
