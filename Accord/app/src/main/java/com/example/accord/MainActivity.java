package com.example.accord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.accord.Auth.EmailAuth;
import com.example.accord.Auth.RegisterService;
import com.example.accord.Auth.RegisterUser;
import com.example.accord.Auth.SignIn;
import com.example.accord.Auth.UserType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // change 1
        startActivity(new Intent(this,SignIn.class));
       // EmailAuth emailAuth=new EmailAuth();
       // emailAuth.signIn("dhruvddevasthale@gmail.com","test123",MainActivity.this);// signs in and prints uid
        //check:https://console.firebase.google.com/u/0/project/accord-b1f26/authentication/users

    }
}