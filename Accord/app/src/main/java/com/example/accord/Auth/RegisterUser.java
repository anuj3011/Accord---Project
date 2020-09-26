package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.R;

public class RegisterUser extends AppCompatActivity {
    String name=null;
    int pincode=0;
    String Phone="";
    String password=null;
    String email=null, add1=null,area=null,city=null;

    EmailAuth emailAuth= new EmailAuth();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

       /*Button registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registerUser(v);
            }
        });*/
    }

    public void registerUser(View view){


        int flag=1;
        TextView textView = (TextView) findViewById(R.id.inputName);
        name =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputNumber);
        Phone =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputZip);
        pincode= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputEmail);
        email=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputAdd1);
        add1=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputCity);
        city=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password = textView.getText().toString();

        if(name.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_LONG).show();
        }
        else if(Phone.length()==0)
        {
            Toast.makeText(getApplicationContext(), "Enter Phone", Toast.LENGTH_LONG).show();
        }
        else if(email.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(pincode).length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Enter Pincode", Toast.LENGTH_LONG).show();
        }
        else if(add1.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "EnterAddress line 1", Toast.LENGTH_LONG).show();
        }
        else if(area.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter the Date of Complaint", Toast.LENGTH_LONG).show();
        }else if(city.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter city", Toast.LENGTH_LONG).show();
        }else if(password.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
        }
        if(flag==1)
        {
//            Intent intent = new Intent(getApplicationContext(),.class);
//            startActivity(intent);
//            finish();
//            // Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();   //to add intent of next activity of user i.e. email verification page
        }

        emailAuth.registerUser(email,password,this);

    }
}