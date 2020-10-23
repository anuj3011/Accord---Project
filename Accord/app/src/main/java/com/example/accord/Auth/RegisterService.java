package com.example.accord.Auth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Nullable;


public class RegisterService extends AppCompatActivity {
    public static final int IMAGE_GALLERY_RESULT = 1;
    ImageView imgPictures;

    String name="";
    String skill="",zip="";
    int flag_main=0;
    String Phone="";

    String add1="",email="",profession="",password="",area="",city="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);
        imgPictures= (ImageView)findViewById(R.id.imgPictures);
        Button registerButton=(Button) findViewById(R.id.button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerService();
            }
        });
    }

    public void registerService(){
        int flag=0;


        TextView textView = (TextView) findViewById(R.id.inputName);
        name =  textView.getText().toString();



        //textView = (TextView) findViewById(R.id.Age);
        //Age= Integer.parseInt(textView.getText().toString());

        textView = (TextView) findViewById(R.id.inputNumber);
        Phone= textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputAdd1);
        add1 =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputZip);
        zip= textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputCity);
        city=textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputEmail);
        email =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.Profession);
        profession =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputSkill);
        skill =  textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password =  textView.getText().toString();
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
        else if(zip.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Enter Pincode", Toast.LENGTH_LONG).show();
        }
        else if(skill.length() == 0)
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
        else{
            flag=1;
        }
        if(flag==1) {
            flag_main=1;
            Intent intent = new Intent(getApplicationContext(),InputDocuments.class);
            startActivity(intent);
            finish();
            // Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
        }


    }

    public void ImageClick(View view)
    {
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setVisibility(View.INVISIBLE);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data,"image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_RESULT) {
                Uri imageUri = data.getData();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    imgPictures.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to open", Toast.LENGTH_LONG).show();
                }


            }
        }
    }
}

