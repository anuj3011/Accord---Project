package com.example.accord.Firestore;

public interface FirebaseTaskInterface {
    public  void onSuccess();
    void onSuccess(Object object);
    void onFailure(String msg);
}
