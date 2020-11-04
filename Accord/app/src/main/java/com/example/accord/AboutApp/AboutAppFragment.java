package com.example.accord.AboutApp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.accord.R;


public class AboutAppFragment extends Fragment {


    private AboutAppModel aboutAppModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        aboutAppModel =
                ViewModelProviders.of(this).get(AboutAppModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutapp, container, false);
        return root;
    }


}