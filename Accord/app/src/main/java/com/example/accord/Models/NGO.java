package com.example.accord.Models;

public class NGO {

    //class defined for NGO_Registration purposes


    private String full_name;
    private long Phone=0;
    private String Address;
    private String email;
    private String profession;
    private String password;


    public NGO ()
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    {
    }

    public NGO (String full_name, long Phone, String Address, String email, String profession, String password)
    {
        //
    }

    public String getfull_name()
    {
        return full_name;
    }
    public long getPhone()
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
