package com.example.accord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.example.accord.Auth.EmailAuth;
import com.example.accord.Auth.RegisterService;
import com.example.accord.Auth.RegisterUser;
import com.example.accord.Auth.SignIn;
import com.example.accord.Auth.UserType;
import com.example.accord.Firestore.StorageAPI;
import com.example.accord.Firestore.UserAPI;
import com.example.accord.YourAccount.YourAccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class IntroActivity extends AppCompatActivity {


    int flag = 1;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void ToLogin(View view) {
        //Log.i("Tag","Button Pressed!!");
        Intent intent = new Intent(getApplicationContext(), OnBoardingIntro.class);
        startActivity(intent);
        finish();
    }

    void testStorage() {
        StorageAPI storageAPI = new StorageAPI();
        File file = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)), "images.png");// fle to upload

        Uri uri = Uri.fromFile(file);
        storageAPI.uploadFile("test", "profileImage", uri, new StorageAPI.StorageTask() {
            @Override
            public void onSuccess(Uri url) {
                //update ui
                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void trackProgress(double val) {
                Log.d("progress", String.valueOf(val));
                //update ui with val
            }

            @Override
            public void onFailure(String msg) {
                //update ui
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }

        });
    }

    void testProfile() {
        startActivity(new Intent(this, YourAccountFragment.class));
    }

    String uid;
    String type;
    UserAPI userAPI=new UserAPI();
    void getUserType() {

            userAPI.getUser("user", uid, new UserAPI.UserTask() {
                @Override
                public void onSuccess(Object object) {
                    type="user";
                    navigateToMainActivity();
                }

                @Override
                public void onFailure(String msg) {
                    userAPI.getUser("sp", uid, new UserAPI.UserTask() {
                        @Override
                        public void onSuccess(Object object) {
                            type="sp";
                            navigateToMainActivity();
                        }

                        @Override
                        public void onFailure(String msg) {
                                userAPI.getUser("ngo", uid, new UserAPI.UserTask() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        type="ngo";
                                        navigateToMainActivity();
                                    }

                                    @Override
                                    public void onFailure(String msg) {
                                                    navigateToMainActivity();// no type found;
                                    }
                                });
                        }
                    });
                }
            });
    }
    void autoLogin() {
        EmailAuth emailAuth = new EmailAuth();
        final FirebaseUser user = emailAuth.checkSignIn();

        if (user == null) {
            navigateToOnBoarding();
        } else {
            uid = user.getUid();
           getUserType();
        }


    }

    void navigateToOnBoarding() {
        Intent intent = new Intent(getApplicationContext(), OnBoardingIntro.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    }

    void navigateToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.putExtra("id", uid);
        intent.putExtra("type", type);
        startActivity(intent);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        autoLogin();
                    }
                },
                3500
        );



    }
}