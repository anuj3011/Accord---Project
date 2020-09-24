package com.example.accord.Firebase;

import com.example.accord.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import static android.provider.Settings.System.getString;

public class GoogleAuth {
    public GoogleSignInOptions gso;
    public GoogleAuth(){
        setSignInOptions();
    }
    public GoogleSignInClient mGoogleSignInClient;
    void setSignInOptions(){
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("607380724191-12g6hco92423l6ppk0nn1jh40idcjl40.apps.googleusercontent.com")
                .requestEmail()
                .build();

    }
}
