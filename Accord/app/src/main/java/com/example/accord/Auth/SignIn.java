package com.example.accord.Auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.Firebase.EmailAuth;
import com.example.accord.Firebase.GoogleAuth;
import com.example.accord.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

public class SignIn extends AppCompatActivity {
    private   int RC_SIGN_IN = 1;
    private long backPressedTime;
    private Toast backToast;
    boolean emailSent=false;
    int flag=0;
    String email;
    String pass;
    EditText textInput;
    EmailAuth emailAuth= new EmailAuth();
    public Button signInButton;
    GoogleAuth googleAuth= new GoogleAuth();
    Activity self;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //Window g = getWindow();
        //g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.activity_sign_in);
        self=this;
        signInButton= (Button)findViewById(R.id.LoginButton) ;
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }


    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }else
        {
            backToast= Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();;
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void signIn(){
        textInput=findViewById(R.id.EmailText);
        email=textInput.getText().toString();
        textInput=findViewById(R.id.PasswordText);
        pass=textInput.getText().toString();
        emailAuth.signIn(email,pass,this);

    }
    public void NewUser(View view){

        signInButton.setText("Register");

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailSent){
                    final FirebaseUser user=emailAuth.mAuth.getCurrentUser();
                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(user.isEmailVerified()){
                                Toast.makeText(getBaseContext(), "Verified", Toast.LENGTH_SHORT).show();
                                // got to dashboard
                                //navigate ahead
                            }
                            else{
                                Toast.makeText(getBaseContext(), "Not Verified", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else {
                   //text input
                    TextView textView = (TextView) findViewById(R.id.PasswordText);
                    pass=textView.getText().toString();
                    textView=findViewById(R.id.EmailText);
                    email=textView.getText().toString();
                    signInButton.setText("Check Verification");
                    emailSent=true;
                    emailAuth.registerUser(email,pass,self);

                }

              // email link
            }
        });



    }

}
