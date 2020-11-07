package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.accord.History.HistoryFragment;
import com.example.accord.UserMainMenu.OrderPage;
import com.example.accord.YourAccount.YourAccountFragment;

import java.util.Random;

public class OrderConfirmation extends AppCompatActivity {

    boolean Success = false;
    boolean Falied = false;

    @Override
    public void onBackPressed(){
        if(Falied) {
            Intent intent = new Intent(getApplicationContext(), OrderPage.class);
            startActivity(intent);
            //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        }
        else if(Success){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            finish();
        }

        else{
            Toast.makeText(getApplicationContext(),"Finding service, going back disabled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        Random d = new Random();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {


                        if(Success){
                            LottieAnimationView lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
                            lottieAnimationView.animate().alpha(0).setDuration(100);
                            TextView textView = (TextView)findViewById(R.id.textView6);
                            textView.animate().alpha(0).setDuration(100);
                            lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView5);
                            lottieAnimationView.animate().alpha(0).setDuration(100);
                            //Toast.makeText(getApplicationContext(),"Press Back to track your order", Toast.LENGTH_LONG).show();
                            LottieAnimationView lottieAnimationView2 = (LottieAnimationView)findViewById(R.id.lottieAnimationView2);
                            lottieAnimationView2.animate().alpha(1).setDuration(750);
                            textView = (TextView)findViewById(R.id.textView5);
                            textView.animate().alpha(1).setDuration(750);
                            //Success = true;
                        }
                        else{
                            Falied = true;
                            LottieAnimationView lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView);
                            TextView textView = (TextView)findViewById(R.id.textView6);
                            lottieAnimationView.animate().alpha(0).setDuration(100);
                            textView.animate().alpha(0).setDuration(100);
                            lottieAnimationView = (LottieAnimationView)findViewById(R.id.lottieAnimationView5);
                            lottieAnimationView.animate().alpha(0).setDuration(100);
                            LottieAnimationView lottieAnimationView2 = (LottieAnimationView)findViewById(R.id.lottieAnimationView4);
                            lottieAnimationView2.animate().alpha(1).setDuration(750);
                            textView = (TextView)findViewById(R.id.textView10);
                            textView.animate().alpha(1).setDuration(750);
                            //Toast.makeText(getApplicationContext(),"Press Back to return to Order_Page", Toast.LENGTH_LONG).show();

                        }

                    }
                },
                4500
        );
    }
}