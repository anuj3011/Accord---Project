package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.accord.Models.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingAPI {
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    Session session = new Session();
    UserAPI firestoreAPI = new UserAPI();

    public void endService(String sessionID, final onEndSession callback) {
        DocumentReference sessionReference = db.collection("sessions").document(sessionID);
        Map<String, Object> update = new HashMap<>();
        update.put("isActive", false);
        update.put("isCompleted", true);
        sessionReference.update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //booked
                    //update  ui

                    callback.onEndSession();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed();
                    }
                }
            }
        });
    }

    public interface BookingTask {
        void onSuccess(List<String> sessionIDs);

        void onFailed(String msg);

        void onSuccess();
    }

    public interface onBooked {
        void onBooked(Session session);

        void onBookingFailed();
    }

    public interface onEndSession {
        void onEndSession();// function to be called when endSession task is completed

        void onFailed();
    }

    public void bookService(String userID, final onBooked onBookedCallBack) {
        final Session session = new Session();
        session.userID = userID;
        session.isActive = true;
        session.isSearchStarted = true;
        session.isCompleted = false;
        session.isAccepted = false;

        DocumentReference sessionReference = db.collection("sessions").document();
        session.sessionID = sessionReference.getId();
        sessionReference.set(session).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onBookedCallBack.onBooked(session);
            }
        });
    }

    public void acceptServiceSession(String serviceProviderID, String sessionID, final BookingTask bookingTask) {
        Map<String, Object> map = new HashMap<>();
        map.put("isAccepted", true);
        map.put("serviceProviderID", serviceProviderID);
        map.put("isActive", true);
        db.collection("sessions").document(sessionID).set(map, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                bookingTask.onSuccess();//accepted
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bookingTask.onFailed(e.getMessage());
            }
        });
    }

    public void getOpenSessionsForProviders(String serviceProviderID, int range, final BookingTask bookingTask) { //un accepted sessions within a range
        db.collection("sessions").whereEqualTo("isActive", true).whereEqualTo("isSearchStarted", true).whereEqualTo("isAccepted", false).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    try {
                        throw error;
                    } catch (FirebaseFirestoreException e) {
                        e.printStackTrace();
                        bookingTask.onFailed(e.getMessage());
                    }
                } else {
                    List<String> sessions = new ArrayList<String>();
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        sessions.add(snapshot.getId());
                    }
                    bookingTask.onSuccess(sessions);
                }
            }
        });
    }

    public void getCurrentSession(String uid, final onBooked sessionCallBack) {// returns a task for current session
        db.collection("sessions").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {

                session = Session.fromSnapshot(snapshot);
                sessionCallBack.onBooked(session);

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
