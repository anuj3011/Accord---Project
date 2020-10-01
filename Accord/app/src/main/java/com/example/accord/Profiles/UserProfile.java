package com.example.accord.Profiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.accord.Firestore.FirestoreAPI;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    FirestoreAPI firestoreAPI = new FirestoreAPI();
    User user = new User();
    FloatingActionButton editButton;
    FloatingActionButton applyButton;

    void getUserProfile() {
        firestoreAPI.getProfile(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(User.class);
                user.uid=documentSnapshot.getId();
                updateUserProfile(user);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getBaseContext(), "No Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void editProfile() {
        EditText textView = findViewById(R.id.userNameText);
        user.name = textView.getText().toString();
        textView = findViewById(R.id.userPhoneText);
        user.phone = textView.getText().toString();
        textView = findViewById(R.id.userEmailText);
        user.email = textView.getText().toString();
        textView = findViewById(R.id.userAddressText);
        user.add1 = textView.getText().toString();
        firestoreAPI.pushUserDetails("user",user.uid,user);
        applyButton.setAlpha((float) 0.0);
        setTextViewEnable(R.id.userNameText,false);
        setTextViewEnable(R.id.userPhoneText,false);
        setTextViewEnable(R.id.userEmailText,false);
        setTextViewEnable(R.id.userAddressText,false);
        Toast.makeText(getBaseContext(), "Details Updated", Toast.LENGTH_SHORT).show();
    }

    void setText(int textId, String text) {
        EditText textView = findViewById(textId);

        textView.setText(text);
    }

    void setTextViewEnable(int id,boolean enabled) {
        EditText textView = findViewById(id);

        textView.setEnabled(enabled);
    }

    void updateUserProfile(User user) {
        setText(R.id.userNameText, user.name);
        setText(R.id.userPhoneText, user.phone);
        setText(R.id.userEmailText, user.email);
        setText(R.id.userAddressText, user.add1);

        editButton = findViewById(R.id.editUserProfileButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewEnable(R.id.userNameText,true);
                setTextViewEnable(R.id.userPhoneText,true);
                setTextViewEnable(R.id.userEmailText,true);
                setTextViewEnable(R.id.userAddressText,true);
                applyButton=findViewById(R.id.applyButton);
                applyButton.setAlpha((float) 1.0);

                applyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editProfile();
                    }
                });
            }
        });
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