package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.example.accord.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookingAPI {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Session session=new Session();
    FirestoreAPI firestoreAPI = new FirestoreAPI();

    public void bookService(String userID, String serviceProviderID) {
        session.userID = userID;
        session.serviceProviderID = serviceProviderID;
        DocumentReference sessionReference = db.collection("sessions").document();
        session.sessionID = sessionReference.getId();
       sessionReference.set(session).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //booked
                    //update  ui
                    Log.d("session","booked !");
                }
                else {
                    try {
                        throw task.getException();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getCurrentSession(String uid) {// returns a task for current session
        db.collection("sessions").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

                session = Session.fromSnapshot(snapshot);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("error", e.getMessage());
            }
        });
        ;

    }

    void cancelBooking(String uid) {

    }


}
