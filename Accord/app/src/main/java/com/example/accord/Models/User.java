package com.example.accord.Models;

public class User {

    //class defined for user registeration purposes.



    public int pincode;
    public String phone;
    public String password;
    public String email;
    public String add1;
    public String area;
    public String city;
    public String name;
    public String uid;
    public User (){}

    public User( String name,String phone,String email, String add1) {

        this.name=name;
        this.phone=phone;
        this.email=email;
        this.add1=add1;
    }


    public int getpincode() {
        return pincode;
    }

    public String getphone() {
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