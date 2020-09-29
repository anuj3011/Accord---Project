package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.accord.R;
import com.example.accord.SlideUser;
import com.example.accord.SliderAdapter;

public class UserType extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    int flag=0;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SlideUser sliderAdapter;
    private TextView[] Dots;
    boolean flag2 = false;
    int CurPage=0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        viewPager = (ViewPager) findViewById(R.id.SlideViewPager);
        linearLayout = (LinearLayout) findViewById(R.id.DotsLayout);
        sliderAdapter = new SlideUser(this);
        viewPager.setAdapter(sliderAdapter);
        DotsIndicator(0);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void ToSignUp(View view){

        //Toast.makeText(getBaseContext(),CurPage,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        intent.putExtra("Flag",CurPage);
        startActivity(intent);
        finish();
    }

    public void DotsIndicator(int position){

        Dots = new TextView[3];
        linearLayout.removeAllViews();
        for(int i=0;i<Dots.length;i++){
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226;"));
            Dots[i].setTextSize(35);
            Dots[i].setTextColor(getResources().getColor(R.color.transparentwhite));
            linearLayout.addView(Dots[i]);
        }

        if(Dots.length>0){
            Dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }else
        {
            backToast= Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();;
        }
        backPressedTime = System.currentTimeMillis();
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            DotsIndicator(position);
            CurPage = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}