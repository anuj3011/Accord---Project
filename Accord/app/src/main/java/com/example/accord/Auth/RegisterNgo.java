package com.example.accord.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.accord.R;

public class RegisterNgo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ngo);
    }

    public void registerNGO(View view){

        String full_name=null;

        long Phone=0;

        String Address=null,email=null,profession=null,password=null;

        TextView textView = (TextView) findViewById(R.id.inputFirstName);
        full_name =  textView.getText().toString();

//        textView = (TextView) findViewById(R.id.inputLastName);
//        last_name =  textView.getText().toString();

//        textView = (TextView) findViewById(R.id.inputAge);
//        Age= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputPhone);
        Phone= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputAddress);
        Address =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputEmail);
        email =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputProfession);
        profession =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password =  textView.getText().toString();


    }
}