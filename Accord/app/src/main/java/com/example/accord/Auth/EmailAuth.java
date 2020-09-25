package com.example.accord.Auth;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class EmailAuth {
    FirebaseAuth mAuth;// auth object which handles signing in
    FirebaseUser user;
<<<<<<< HEAD:Accord/app/src/main/java/com/example/accord/Auth/EmailAuth.java
    // check 101
=======
>>>>>>> parent of 6496026... email link verification:Accord/app/src/main/java/com/example/accord/Firebase/EmailAuth.java

    public EmailAuth() {
        mAuth = FirebaseAuth.getInstance();
    }

    FirebaseUser checkSignIn() { // checks if user is signed in, call this on app start
        user = mAuth.getCurrentUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }
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
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity.getApplicationContext(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();


                        }

                        // ...
                    }
                });
    }

<<<<<<< HEAD:Accord/app/src/main/java/com/example/accord/Auth/EmailAuth.java
    void registerUser(String email, String pass, final Activity activity) {
=======
    public void registerUser(String email, String pass, final Activity activity) {
>>>>>>> parent of 6496026... email link verification:Accord/app/src/main/java/com/example/accord/Firebase/EmailAuth.java
        mAuth.createUserWithEmailAndPassword(email, pass)

                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            String token = user.getUid();
                            Toast.makeText(activity.getApplicationContext(), "Registered !", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(activity.getApplicationContext(), task.getException().getMessage(),  Toast.LENGTH_LONG).show();
                        }


                        // ...
                    }
                });
    }


}
