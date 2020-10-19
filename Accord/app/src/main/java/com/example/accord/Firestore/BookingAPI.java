package com.example.accord.Firestore;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.accord.Models.Session;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookingAPI {
  public   FirebaseFirestore db = FirebaseFirestore.getInstance();

    void bookService() {

    }

   public Task<DocumentSnapshot> getCurrentSession(String uid) {// returns a task for current session
        return db.collection("sessions").document(uid).get();

    }

    void cancelBooking(String uid) {

    }


}
