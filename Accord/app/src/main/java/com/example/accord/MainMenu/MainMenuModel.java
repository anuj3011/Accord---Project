package com.example.accord.MainMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainMenuModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MainMenuModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is About App fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}