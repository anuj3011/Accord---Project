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
    public EmailAuth(){
        mAuth=FirebaseAuth.getInstance();
    }
    FirebaseUser checkSignIn() { // checks if user is signed in, call this on app start
        user = mAuth.getCurrentUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }
   public void signIn(String email, String pass, Activity activity) {
       mAuth.signInWithEmailAndPassword(email, pass)
               .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();
                            String uid=user.getUid();
                            Log.d("user",uid);

                       } else {
                           // If sign in fails, display a message to the user.
                           Log.w("TAG", "createUserWithEmail:failure", task.getException());


                       }

                       // ...
                   }
               });
   }
    void registerUser(String email, String pass,Activity activity) {
        mAuth.createUserWithEmailAndPassword(email, pass)

                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     try{
                         user=mAuth.getCurrentUser();
                         String token=user.getUid();
                         Log.d("user:",token);
                     }
                     catch (Exception e){
                         Log.d("tag",e.toString());
                     }




                        // ...
                    }
                });
    }


}
