package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.MainActivity;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.R;

import java.io.Serializable;

public class InputDocuments extends AppCompatActivity {

    String id;
    void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("id",  id);
                               intent.putExtra("type","sp");
                               startActivity(intent);
    }
    void getArguments(){
        Bundle args=getIntent().getExtras();
        if(args!=null){
            id=  args.getString("id");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_documents);
        getArguments();
        Button button=findViewById(R.id.skipDocsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

    }
}