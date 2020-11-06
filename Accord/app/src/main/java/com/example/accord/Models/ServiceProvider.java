package com.example.accord.Models;

import com.google.android.gms.maps.model.LatLng;

public class ServiceProvider extends CustomUser {

    //class defined for Service provider registeration purposes.

    public String first_name;
    public String last_name;
    public boolean isSkilled;
    public int Age;
    public  String category;

    public String Address;
    public String email;
    public String profession;
    public String password;
    String phone;
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
    public String getfirst_name()
    {
        return first_name;
    }

    public String getlast_name()
    {
        return last_name;
    }
    public boolean getisSkilled(){
        return isSkilled;
    }
    public int getAge()
    {
        return Age;
    }

  

    public String getAddress()
    {
        return Address;
    }

    public String getemail()
    {
        return email;
    }

    public String getprofession()
    {
        return profession;
    }

    public String getpassword()
    {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
