package com.example.accord.Models;

public class User {

    //class defined for user registeration purposes.


    private int Age;
    private int pincode;
    private String Phone;
    private String password;
    private String email;
    private String add1;
    private String area;
    private String city;
    String type="user";
    public User (){}

    public User(int Age, int pincode, String Phone, String password, String email, String add1, String area, String city) {
        // ...
    }
    public String getType(){
        return type;
    }
    public int getAge() {
        return Age;
    }

    public int getpincode() {
        return pincode;
    }

    public String getPhone() {
        return Phone;
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