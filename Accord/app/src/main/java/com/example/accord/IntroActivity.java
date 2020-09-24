package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;

import android.view.View;

import java.util.Random;

public class IntroActivity extends AppCompatActivity
{
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
    public void ToLogin(View view)
    {
        //Log.i("Tag","Button Pressed!!");
        Intent intent = new Intent(getApplicationContext(),OnBoardingIntro.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        // change 1
        //startActivity(new Intent(this,SignIn.class));
       // EmailAuth emailAuth=new EmailAuth();
       // emailAuth.signIn("dhruvddevasthale@gmail.com","test123",MainActivity.this);// signs in and prints uid
        //check:https://console.firebase.google.com/u/0/project/accord-b1f26/authentication/users
        Random d = new Random();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        final LottieAnimationView lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
                        lottieAnimationView.animate().alpha(0).setDuration(500);
                        LottieAnimationView lottieAnimationView2 = (LottieAnimationView)findViewById(R.id.lottieAnimationView2);
                        lottieAnimationView2.animate().alpha(1).setDuration(1250);
                        lottieAnimationView2.setProgress(0f);
                        lottieAnimationView2.setClickable(true);
                        boolean isAnimating = true;
                        while (isAnimating) {
                            if (lottieAnimationView2.getProgress() == 1f)
                                lottieAnimationView2.pauseAnimation();
                        }

                    }
                },
                4500
        );


    }
}