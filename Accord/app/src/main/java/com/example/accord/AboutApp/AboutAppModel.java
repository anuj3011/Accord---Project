package com.example.accord.AboutApp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutAppModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutAppModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is About App fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}