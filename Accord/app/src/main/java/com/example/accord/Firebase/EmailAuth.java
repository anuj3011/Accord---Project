package com.example.accord.Firebase;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.accord.Auth.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailAuth {
    public FirebaseAuth mAuth;// auth object which handles signing in
    public FirebaseUser user;

    public EmailAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    boolean checkSignIn() { // checks if user is signed in, call this on app start
        user = mAuth.getCurrentUser();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public  void sendEmailLink(final SignIn activity) {

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                            activity.signInButton.setText("Check Link");
                        }
                        else{

                            Log.d("TAG", task.getException().toString());
                        }
                    }
                });

    }
    public void signIn(String email, String pass, final Activity activity) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();

                            String uid = user.getUid();
                            Log.d("user", uid);
                            Toast.makeText(activity.getApplicationContext(), "Signed In!", Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "Please Check your credentials", task.getException());
                            Toast.makeText(activity.getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();


                        }

                        // ...
                    }
                });
    }

    public void registerUser(String email, String pass, final Activity activity) {

        mAuth.createUserWithEmailAndPassword(email, pass)

                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            sendEmailLink((SignIn) activity);

                            Toast.makeText(activity.getApplicationContext(), "Checking Verification", Toast.LENGTH_LONG).show();



                        } else {
                            if(checkSignIn()){
                                Toast.makeText(activity.getApplicationContext(), "Signed in,Checking Verification", Toast.LENGTH_LONG).show();
                            }
                            Toast.makeText(activity.getApplicationContext(), task.getException().toString(),  Toast.LENGTH_LONG).show();
                        }


                        // ...
                    }
                });
    }


}

