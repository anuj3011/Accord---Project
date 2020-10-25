package com.example.accord.AboutApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.accord.OnBoardingIntro;
import com.example.accord.R;
import com.example.accord.ServicesOption;


public class AboutAppFragment extends Fragment {


    private AboutAppModel aboutAppModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        aboutAppModel =
                ViewModelProviders.of(this).get(AboutAppModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutapp, container, false);
        return root;
    }

    public void ToServices(View view){
        Intent intent = new Intent(getActivity(),ServicesOption.class);
        startActivity(intent);
    }
}