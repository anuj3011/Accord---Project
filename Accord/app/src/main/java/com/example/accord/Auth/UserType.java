package com.example.accord.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.accord.R;
import com.example.accord.UserMainMenu.SlideUser;

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
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            Dots[position].setTextColor(getResources().getColor(R.color.black1));
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