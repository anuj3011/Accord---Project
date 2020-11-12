package com.example.accord.Auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.accord.Firestore.UserAPI;
import com.example.accord.IntroActivity;
import com.example.accord.MainActivity;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.example.accord.OnBoardingIntro;
import com.example.accord.R;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

public class SignIn extends AppCompatActivity {
    private int RC_SIGN_IN = 1;
    private long backPressedTime;
    private Toast backToast;
    boolean emailSent = false;
    int flag = 0;
    String email;
    String pass;
    EditText textInput;
    EmailAuth emailAuth = new EmailAuth();
    public Button signInButton;
    TextView newUserText;
    Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Window g = getWindow();
        //g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_sign_in);
        self = this;
        signInButton = (Button) findViewById(R.id.RegisterButton);
        newUserText = findViewById(R.id.TextView);
        Intent intent = getIntent();
        final int flag = intent.getIntExtra("Flag", 0);
        newUserText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NewUser();


                Intent intent;
                if(flag==0)
                    intent = new Intent(getApplicationContext(), RegisterUser.class);
                else if(flag==1)
                    intent = new Intent(getApplicationContext(), RegisterService.class);
                else
                    intent = new Intent(getApplicationContext(), RegisterNgo.class);

                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                //finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
            ;
        }
        backPressedTime = System.currentTimeMillis();
    }
    String userId;
    String type;
    UserAPI firestoreAPI=new UserAPI();
    void getUserProfile() {
        userId = emailAuth.checkSignIn().getUid();
        if(flag==0){
            type="user";
        }
        else if(flag==1){
            type="sp";
        }
        else{
            type="ngo";
        }
        if(userId==null || userId.length()<1){

            emailAuth.logout();
            Toast.makeText(getApplicationContext(), "Logging out", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, IntroActivity.class));

        }
        firestoreAPI.getUser(type, userId, new UserAPI.UserTask() {
            @Override
            public void onSuccess(Object object) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("id", userId);
                intent.putExtra("type","user");
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
    public void signIn() {
        textInput = findViewById(R.id.Email);
        email = textInput.getText().toString();
        textInput = findViewById(R.id.Password);
        pass = textInput.getText().toString();
        emailAuth.signIn(email, pass, this, new EmailAuth.AuthTask() {
            @Override
            public void onComplete(String uid) {
                //navigate here
              userId=uid;
              getUserProfile();
            }

            @Override
            public void onComplete() {
          //   getUserProfile();
            }

            @Override
            public void onEmailSent() {

            }

            @Override
            public void onFailure(String msg) {
                //auto login failed
            }
        });

    }




}
