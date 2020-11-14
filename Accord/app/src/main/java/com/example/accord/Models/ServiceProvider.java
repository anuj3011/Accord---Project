package com.example.accord.Models;

import com.google.android.gms.maps.model.LatLng;

public class ServiceProvider extends CustomUser {



    public String first_name;
    public String last_name;
    public boolean isSkilled;

    public  String category;

    public String address;
    public String email;
    public String profession;
    public String password;
    public String phone;
    public String type="sp";

    public ServiceProvider ()
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    {
    }

    public String getCategory() {
        return category;
    }

    public String getType(){
        return type;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public boolean getIsSkilled(){
        return isSkilled;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getProfession() {
        return profession;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
