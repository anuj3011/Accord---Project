package com.example.accord.UserMainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.accord.R;

public class SlideUser extends PagerAdapter {
    int CurPage;

    Context context;
    LayoutInflater layoutInflater;
    public SlideUser(Context context){
        this.context = context;
    }

    public int[] SlideAnimations = {
            R.raw.media,
            R.raw.freelancer,
            R.raw.house,
    };

    public String[] SlideHeadings={
            "User",
            "Service provider",
            "NGO",
    };


    @Override
    public int getCount() {
        return SlideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slide_user,container,false);

        LottieAnimationView SlideAnimation = (LottieAnimationView) view.findViewById(R.id.lottieAnimationView);
        TextView SlideHeading = (TextView) view.findViewById(R.id.Heading);
        TextView SlideText = (TextView) view.findViewById(R.id.Text);
        SlideAnimation.setAnimation(SlideAnimations[position]);
        SlideHeading.setText(SlideHeadings[position]);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }

}