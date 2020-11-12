package com.example.accord.Auth;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.accord.Firestore.UserAPI;
import com.example.accord.MainActivity;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.example.accord.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.annotation.Nullable;


public class RegisterService extends AppCompatActivity {
    public static final int IMAGE_GALLERY_RESULT = 1;
    ImageView imgPictures;

    String name = "";
    String skill = "", zip = "";
    int flag_main = 0;
    String Phone = "";

    String add1 = "", email = "", profession = "", password = "", area = "", city = "";
    boolean emailSent = false;
    EmailAuth emailAuth = new EmailAuth();
    ServiceProvider serviceProvider = new ServiceProvider();
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);
        imgPictures = (ImageView) findViewById(R.id.imgPictures);
        registerButton = (Button) findViewById(R.id.confirmOrderButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerService();
            }
        });
    }

    public void registerService() {
        int flag = 0;


        TextView textView = (TextView) findViewById(R.id.name);
        name = textView.getText().toString();


        textView = (TextView) findViewById(R.id.number);
        Phone = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputAdd1);
        add1 = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputArea);
        area = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputZip);
        zip = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputCity);
        city = textView.getText().toString();

        textView = (TextView) findViewById(R.id.address);
        email = textView.getText().toString();

        textView = (TextView) findViewById(R.id.Profession);
        profession = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputSkill);
        skill = textView.getText().toString();

        textView = (TextView) findViewById(R.id.inputPass);
        password = textView.getText().toString();

        if (name.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_LONG).show();
        } else if (Phone.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter Phone", Toast.LENGTH_LONG).show();
        } else if (email.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
        } else if (zip.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter Pincode", Toast.LENGTH_LONG).show();
        } else if (skill.length() == 0) {
            Toast.makeText(getApplicationContext(), "Enter Age", Toast.LENGTH_LONG).show();
        } else if (add1.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "EnterAddress line 1", Toast.LENGTH_LONG).show();
        } else if (profession.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter Profession", Toast.LENGTH_LONG).show();
        } else if (area.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter area", Toast.LENGTH_LONG).show();
        } else if (city.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter city", Toast.LENGTH_LONG).show();
        } else if (password.length() == 0) {
            flag = 0;
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show();
        } else {
            flag = 1;
        }
        if (flag == 1) {
            serviceProvider.address = add1;
            serviceProvider.first_name = name;
            serviceProvider.email = email;
            serviceProvider.phone = Phone;
            serviceProvider.profession = profession;
            serviceProvider.isSkilled = true;


            flag_main = 1;
//            // Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
            NewServiceProvider();
        }


    }

    public void NewServiceProvider() {


        if (emailSent) {
            final FirebaseUser firebaseUser = emailAuth.mAuth.getCurrentUser();

            firebaseUser.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(getBaseContext(), "Verified", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getBaseContext(), "Completing Registration Process", Toast.LENGTH_LONG).show();

                        new UserAPI().pushUser("sp", firebaseUser.getUid(), serviceProvider, new UserAPI.UserTask() {
                            @Override
                            public void onSuccess(Object object) {
                                serviceProvider = (ServiceProvider) object;
                                Intent intent = new Intent(getApplicationContext(), InputDocuments.class);
                                intent.putExtra("id", firebaseUser.getUid());
                                intent.putExtra("type", "sp");
                                startActivity(intent);
                                finish();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                intent.putExtra("user", (Serializable) serviceProvider);
//                                intent.putExtra("type","sp");
//                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                        // got to dashboard
                        //navigate ahead to Profile Page
                    } else {
                        Toast.makeText(getBaseContext(), "Not Verified", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            if (flag_main == 1) {


                registerButton.setText("Check Email Verification");
                registerButton.setTextSize(10);
                emailAuth.registerUser(email, password, new EmailAuth.AuthTask() {
                    @Override
                    public void onComplete(String uid) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onEmailSent() {
                        emailSent = true;

                        Toast.makeText(getApplicationContext(), "Email Sent", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });
            }


        }

        // email link
    }

    public void ImageClick(View view) {
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setVisibility(View.INVISIBLE);
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String pictureDirectoryPath = pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");
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

