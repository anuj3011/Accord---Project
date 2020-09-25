package com.example.accord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accord.Auth.SignIn;
import com.example.accord.Auth.UserType;

public class OnBoardingIntro extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    int flag=0;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] Dots;
    boolean flag2 = false;
    int CurPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_intro);
        viewPager = (ViewPager) findViewById(R.id.SlideViewPager);
        linearLayout = (LinearLayout) findViewById(R.id.DotsLayout);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        DotsIndicator(0);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void ToSignUp(View view){

        Intent intent = new Intent(getApplicationContext(), UserType.class);
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

            if(CurPage==2){
                Button button = (Button) findViewById(R.id.SkipButton);
                button.setText("Login");
                flag2 = true;
            }
            else if(flag2){

                Button button = (Button) findViewById(R.id.SkipButton);
                button.setText("Skip >>");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}