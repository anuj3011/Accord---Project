package com.example.accord.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.accord.R;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
    }
    public void registerUser(View view){
        String name=null;
        int Age=0,pincode;
        long Phone=0;
        String password=null;
        String email=null, add1=null,area=null,city=null;


        TextView textView = (TextView) findViewById(R.id.inputName);
        name =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputNumber);
        Phone =  Integer.parseInt(textView.getText().toString());

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


    }
}