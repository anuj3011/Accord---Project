package com.example.accord.YourAccount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class YourAccountModel extends ViewModel {

    private MutableLiveData<String> mText;

    public YourAccountModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Your Account fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}