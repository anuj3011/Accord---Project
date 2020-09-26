package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.R;

public class RegisterService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);
    }

    public void registerService(View view){

        String name=null;
        int Age=0,zip,flag=1;
        long Phone=0;

        String add1=null,email=null,profession=null,password=null,area=null,city=null;

        TextView textView = (TextView) findViewById(R.id.inputName);
        name =  textView.getText().toString();



        textView = (TextView) findViewById(R.id.Age);
        Age= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputNumber);
        Phone= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputAdd1);
        add1 =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputZip);
        zip= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputCity);
        city=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputEmail);
        email =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputProfession);
        profession =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password =  textView.getText().toString();
        if(name.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(Phone).length()==0)
        {
            Toast.makeText(getApplicationContext(), "Enter Phone", Toast.LENGTH_LONG).show();
        }
        else if(email.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(zip).length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Enter Pincode", Toast.LENGTH_LONG).show();
        }
        else if(String.valueOf(Age).length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Enter Age", Toast.LENGTH_LONG).show();
        }
        else if(add1.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "EnterAddress line 1", Toast.LENGTH_LONG).show();
        }
        else if(profession.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter Profession", Toast.LENGTH_LONG).show();
        }
        else if(area.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter area", Toast.LENGTH_LONG).show();
        }else if(city.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter city", Toast.LENGTH_LONG).show();
        }else if(password.length()==0)
        {
            flag=0;
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
        }
        if(flag==1) {
            Intent intent = new Intent(getApplicationContext(),InputDocuments.class);
            startActivity(intent);
            finish();
            // Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
        }


    }
}

