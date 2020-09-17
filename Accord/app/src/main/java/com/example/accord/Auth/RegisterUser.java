package com.example.accord.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.accord.R;

public class RegisterUser extends AppCompatActivity {
    String name=null;
    int Age=0,pincode;
    String Phone="";
    String password=null;
    String email=null, add1=null,area=null,city=null;

    EmailAuth emailAuth= new EmailAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

       Button registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registerUser(v);
            }
        });
    }
    public void registerUser(View view){



        TextView textView = (TextView) findViewById(R.id.inputName);
        name =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputNumber);
        Phone =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputEMAIL);
        email=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputAdd1);
        add1=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputCity);
        city=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password = textView.getText().toString();

        emailAuth.registerUser(email,password,this);

    }
}