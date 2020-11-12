package com.example.accord.Auth;

import android.app.Activity;
import android.content.Intent;
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

    public FirebaseUser checkSignIn() { // checks if user is signed in, call this on app start
        user = mAuth.getCurrentUser();
        return user;
    }

    public void logout() {
        mAuth.signOut();
    }

    public void sendEmailLink(final AuthTask authTask) {

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            authTask.onComplete();


                        } else {

                            authTask.onFailure(task.getException().getMessage());
                        }
                    }
                });

    }

    public interface AuthTask {
        void onComplete(String uid);

        void onComplete();
        void onEmailSent();
        void onFailure(String msg);
    }

    public void signIn(String email, String pass, final Activity activity, final AuthTask authTask) {
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
//                            Intent i = new Intent(getActivity())
//                            startActivity(i);
//                            ((Activity) getActivity()).overridePendingTransition(0, 0);
                            authTask.onComplete(uid);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "Please Check your credentials", task.getException());
                            Toast.makeText(activity.getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();


                        }

                        // ...
                    }
                });
    }

    public void registerUser(String email, String pass, final AuthTask authTask) {
        try{
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();

                        authTask.onEmailSent();
                        sendEmailLink(authTask);


                    } else {
                        authTask.onFailure(task.getException().getMessage());
                    }
                }
            });
        }
        catch (Exception e){
            authTask.onFailure(e.getMessage());
        }



    }


}
