package com.example.accord.Models;

import com.google.android.gms.maps.model.LatLng;

public class User extends CustomUser{

    //class defined for user registeration purposes.


    private int Age;
    private int pincode;
    private String phone;
    private String password;
    private String email;
    String name;
    private String add1;
    private String area;
    private String city;
    String type="user";
    int orderCount;
    int donationCount;

    public User (){}

    public User(String name,String Phone, String email, String add1, String area, String city) {
        this.name=name;
        this.phone=Phone;
        this.email=email;
        this.add1=add1;
        this.area=area;
        this.city=city;

    }

    public int getOrderCount(){
        return  orderCount;
    }
    public  int getDonationCount(){
        return  donationCount;
    }
    public String getType(){
        return type;
    }
    public int getAge() {
        return Age;
    }
    public String getName(){
        return name;
    }
    public int getpincode() {
        return pincode;
    }

    public String getPhone() {
        return phone;
    }

    public String getpassword() {
        return password;
    }

    public String getemail() {
        return email;
    }

    public String getadd1() {
        return add1;
    }

    public String getarea(){
        return area;
    }

    public String getcity(){
        return city;
    }

}