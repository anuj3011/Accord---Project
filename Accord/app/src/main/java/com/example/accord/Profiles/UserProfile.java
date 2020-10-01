package com.example.accord.Profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accord.Firestore.FirestoreAPI;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;


import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfile extends AppCompatActivity {
    String uid = " ";
    FirestoreAPI firestoreAPI=new FirestoreAPI();
    User user = new User();

    void getUserProfile() {
        firestoreAPI.getProfile(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                updateUserProfile(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void setText(int textId,String text){
        TextView textView=findViewById(textId);
        textView.setText(text);
    }
    void updateUserProfile(User user){
       setText(R.id.userNameText,user.name);
       setText(R.id.userPhoneText,user.phone);
       setText(R.id.userEmailText,user.email);
       setText(R.id.userAddressText,user.add1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        this.uid = b.getString("uid");

        setContentView(R.layout.activity_user_profile);
        getUserProfile();
    }
}