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
            R.drawable.icon1,
            R.drawable.icon2,
            R.drawable.icon3,
    };

    public String[] SlideHeadings={
            "Heading1",
            "Heading2",
            "Heading3",
    };

    public String[] SlideTexts={
            "Industrialization is central if any economy is to be successful and the policy attempts at industrialization involve creating systems and " +
                    "institutional arrangements that can help accelerate the process of industrialization.",
            "Industrialization is central if any economy is to be successful and the policy attempts at industrialization involve creating systems and " +
                    "institutional arrangements that can help accelerate the process of industrialization.",
            "Industrialization is central if any economy is to be successful and the policy attempts at industrialization involve creating systems and " +
                    "institutional arrangements that can help accelerate the process of industrialization.",
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
