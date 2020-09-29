package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FirestoreAPI {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirestoreAPI() {
        db = FirebaseFirestore.getInstance();
    }

    public void getNGO(String uid) {
        db.collection("ngo")// collection reference
                .document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                NGO ngo = documentSnapshot.toObject(NGO.class);
                Log.d("ngo",ngo.toString());
            }
        });
    }

    public void getUser(String uid) {
        db.collection("user")// collection reference
                .document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User ngo = documentSnapshot.toObject(User.class);
            }
        });
    }

    public void getServiceProvider(String uid) {
        db.collection("ngo")// collection reference
                .document(uid)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ServiceProvider ngo = documentSnapshot.toObject(ServiceProvider.class);
            }
        });
    }

    public void pushFirestore(String type, String uid, Object object) {
        // Add a new document with a generated ID

        db.collection(type)
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
