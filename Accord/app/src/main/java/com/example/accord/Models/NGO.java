package com.example.accord.Models;

public class NGO extends CustomUser{

    //class defined for NGO_Registration purposes


    private String full_name;
    private String Phone=" ";
    private String Address;
    private String email;
    private String profession;
    private String password;
   public String type="ngo";


    public NGO ()
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    {

    }

public String getType(){
        return type;
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
