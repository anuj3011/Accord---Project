package com.example.accord.Auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.AddingRecord;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.MainActivity;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class RegisterUser extends AppCompatActivity {
    String name = null;
    String pincode = "";
    String Phone = "";
    String password="";
    String email="", add1 = "", area = "", city = "";
    private int RC_SIGN_IN = 1;
    private long backPressedTime;
    private Toast backToast;
    boolean emailSent = false;
    int flag_main = 0;
    EditText textInput;
    EmailAuth emailAuth = new EmailAuth();
    public Button signInButton;
    Activity self;
    User user= new User();
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        self = this;
        registerButton=(Button) findViewById(R.id.confirmOrderButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser() {

        int flag=0;

        TextView textView = (TextView) findViewById(R.id.name);
        name = textView.getText().toString();

        textView = (TextView) findViewById(R.id.address);
        email = textView.getText().toString();

        textView = (TextView) findViewById(R.id.number);
        Phone = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputZip);
        pincode = textView.getText().toString();


        textView = (TextView) findViewById(R.id.inputPass);
        password = textView.getText().toString();


        textView = (TextView) findViewById(R.id.inputAdd1);
        add1 = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputCity);
        city = textView.getText().toString();



        if (name.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_LONG).show();
        } else if (Phone.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter Phone", Toast.LENGTH_LONG).show();
        } else if (email.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
        } else if (pincode.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter Pincode", Toast.LENGTH_LONG).show();
        } else if (add1.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "EnterAddress line 1", Toast.LENGTH_LONG).show();
        } else if (area.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter the Date of Complaint", Toast.LENGTH_LONG).show();
        } else if (city.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter city", Toast.LENGTH_LONG).show();
        } else if (password.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
        }else{
            flag=1;
        }
        if (flag == 1) {
                flag_main=1;

              NewUser();
//            Intent intent = new Intent(getApplicationContext(),.class);
//            startActivity(intent);
//            finish();
//            // Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();   //to add intent of next activity of user i.e. email verification page
        }


    }

    public void NewUser() {



                if (emailSent) {
                    final FirebaseUser firebaseUser = emailAuth.mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    firebaseUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (firebaseUser.isEmailVerified()) {
                                Toast.makeText(getBaseContext(), "Verified", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getBaseContext(), "Completing Registration Process", Toast.LENGTH_LONG).show();
                                user=new User(name,Phone,email,add1,area,city);
                                user.uid=firebaseUser.getUid();
                                new UserAPI().pushUser("user", firebaseUser.getUid(), user, new UserAPI.UserTask() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Intent intent = new Intent(getApplicationContext(), AddingRecord.class);
                                        //intent.putExtra("id", firebaseUser.getUid());
                                        //intent.putExtra("type","user");
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                                    }
                                });
                                // got to dashboard
                                //navigate ahead to Profile Page
                            } else {
                                Toast.makeText(getBaseContext(), "Not Verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {

                    if(flag_main==1){
                        emailSent = true;
                        registerButton.setText("Check Email Verification");
                        registerButton.setTextSize(12);
                        emailAuth.registerUser(email, password, new EmailAuth.AuthTask() {
                            @Override
                            public void onComplete(String uid) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onEmailSent() {

                                //Intent intent = new Intent(getApplicationContext(), AddingRecord.class);
                                //intent.putExtra("id", firebaseUser.getUid());
                                //intent.putExtra("type","user");
                                //startActivity(intent);
                                //finish();
                                Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                }

                // email link
            }



}
