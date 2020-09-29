package com.example.accord.Models;

public class NGO {

    //class defined for NGO_Registration purposes


    private String full_name;
    private String Phone=" ";
    private String Address;
    private String email;
    private String profession;
    private String password;
    String uid;

    public NGO ()
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    {
    }

    public NGO (String full_name, String Phone, String Address, String email, String profession)
    {
        this.full_name=full_name;
        this.Phone=Phone;
        this.Address=Address;
        this.email=email;
        this.profession=profession;

    }

    public String getfull_name()
    {
        return full_name;
    }
    public String getPhone()
    {
        return Phone;
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



}
