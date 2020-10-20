package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.accord.Auth.SignIn;

import com.example.accord.Models.Session;

public class MainActivity extends AppCompatActivity {

    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(this, SignIn.class));
        // EmailAuth emailAuth=new EmailAuth();
        // emailAuth.signIn("dhruvddevasthale@gmail.com","test123",MainActivity.this);// signs in and prints uid
        //check:https://console.firebase.google.com/u/0/project/accord-b1f26/authentication/users

    }
}