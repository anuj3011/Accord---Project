package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreAPI {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirestoreAPI() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getProfile(String uid) {
       return db.collection("users")// collection reference
                .document(uid)
                .get();
    }


    public void pushUserDetails(String type, String uid, Object object) {
        // Add a new document with a generated ID

        db.collection("users")
                .document(uid)
                .set(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("document", "added");
                } else {
                    Log.d("document", task.getException().getMessage());
                }
            }
        });
    }
}
