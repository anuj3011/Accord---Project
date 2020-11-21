package com.example.accord.Firestore;

import androidx.annotation.NonNull;

import com.example.accord.Models.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderHistoryAPI {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public void pushOrder(String uid, String  session, final FirebaseTaskInterface firebaseTaskInterface) {
        firebaseFirestore.collection("user").document(uid).update("orders", FieldValue.arrayUnion(session)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseTaskInterface.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseTaskInterface.onFailure(e.getMessage());
            }
        });
    }


}
