package com.example.accord.Auth;

import android.os.Bundle;

import com.example.accord.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn extends AppCompatActivity {
    String email;
    String pass;
    EditText textInput;
    EmailAuth emailAuth=new EmailAuth();
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_in);
        signInButton=(Button) findViewById(R.id.loginButton) ;
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }
    public  void signIn(){
        textInput=findViewById(R.id.signInEmail);
        email=textInput.getText().toString();
        textInput=findViewById(R.id.signInPass);
        pass=textInput.getText().toString();
        emailAuth.signIn(email,pass,this);

    }

}
