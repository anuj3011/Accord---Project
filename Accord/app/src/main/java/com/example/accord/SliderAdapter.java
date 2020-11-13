package com.example.accord;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] SlideImages = {
            R.drawable.serviceuserintro,
            R.drawable.donationintropage,
            R.drawable.serviceproviderintro,
    };

    public String[] SlideHeadings={
            "Welcome To Accord",
            "Welcome To Accord",
            "Welcome To Accord",
    };

    public String[] SlideTexts={
            "If you are looking for an easy and affordable way to get your home chores done by professionals in no time, then this is the App/ place for you.",
            "Being able to donate your belongings to an NGO whilst sitting in your comfortable home, how does it sound? Accord enables you to feel the contentment after donating from the comfort of your home with just some clicks",
            "Do you find it difficult to be an independent contractor? How does getting customers while still being your own boss sounds?\n" +
                    "Register on Accord and join our family and be your own boss",
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
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView SlideImage = (ImageView) view.findViewById(R.id.Image);
        TextView SlideHeading = (TextView) view.findViewById(R.id.Heading);
        TextView SlideText = (TextView) view.findViewById(R.id.Text);
        SlideImage.setImageResource(SlideImages[position]);
        SlideHeading.setText(SlideHeadings[position]);
        SlideText.setText(SlideTexts[position]);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
