package com.example.accord.YourAccount;

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

import com.example.accord.R;

public class YourAccountFragment extends Fragment {

    private YourAccountModel yourAccountModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        yourAccountModel =
                ViewModelProviders.of(this).get(YourAccountModel.class);
        View root = inflater.inflate(R.layout.fragment_youraccount, container, false);

        return root;
    }
}