package com.example.accord;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.Auth.SignIn;
import com.example.accord.Firestore.FirestoreAPI;
import com.example.accord.Models.NGO;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change 1
        FirestoreAPI firestoreAPI = new FirestoreAPI();
        firestoreAPI.pushFirestore("ngo", "uid", new NGO("full_name", "Phone", "Address", "email", "profession"));
        firestoreAPI.getNGO("uid");
        startActivity(new Intent(this, SignIn.class));
        // EmailAuth emailAuth=new EmailAuth();
        // emailAuth.signIn("dhruvddevasthale@gmail.com","test123",MainActivity.this);// signs in and prints uid
        //check:https://console.firebase.google.com/u/0/project/accord-b1f26/authentication/users

    }
}