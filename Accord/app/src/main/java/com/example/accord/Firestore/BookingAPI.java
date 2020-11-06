package com.example.accord.Firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.Session;
import com.example.accord.Models.User;
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

    public interface onBooked {
        void onBooked(Session session);

        void onBookingFailed();
    }

    public interface onEndSession {
        void onEndSession();// function to be called when endSession task is completed

        void onFailed();
    }

    public interface BookingTask {
        void onSuccess(List<Session> sessions);

        void onSuccess();

        void onFailed(String msg);
    }

    public void endService(String sessionID, final onEndSession callback) {
        DocumentReference sessionReference = db.collection("sessions").document(sessionID);
        Map<String, Object> update = new HashMap<>();
        update.put("isActive", false);
        update.put("isCompleted", true);
        sessionReference.update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // booked
                    // update ui

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


    public void bookService(String userID, final onBooked onBookedCallBack) {
        session.userID = userID;
        session.isActive = true;
        session.isCompleted = false;
        session.isSearchStarted = true;

        DocumentReference sessionReference = db.collection("sessions").document();
        session.sessionID = sessionReference.getId();
        sessionReference.set(session).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onBookedCallBack.onBooked(session);
            }
        });
    }


    public void acceptServiceSession(String serviceProviderID, final String sessionID, final BookingTask bookingTask) {
        Map<String, Object> map = new HashMap<>();
        map.put("isAccepted", true);
        map.put("serviceProviderID", serviceProviderID);
        map.put("isActive", true);
        map.put("isSearchStarted", false);
        db.collection("sessions").document(sessionID).set(map, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        bookingTask.onSuccess();// accepted
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bookingTask.onFailed(e.getMessage());
            }
        });
    }

    public void getOpenSessionsForProviders(final ServiceProvider serviceProvider, final BookingTask bookingTask) { // un

        db.collection("sessions").whereEqualTo("isActive", true).whereEqualTo("isSearchStarted", true)
                .whereEqualTo("isAccepted", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            try {
                                throw error;
                            } catch (FirebaseFirestoreException e) {
                                e.printStackTrace();
                                bookingTask.onFailed(e.getMessage());
                            } catch (Exception exception) {
                                bookingTask.onFailed(exception.getMessage());
                            }
                        } else {
                            List<Session> sessions = new ArrayList<Session>();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                Session session = snapshot.toObject(Session.class);
                                sessions.add(session);
                            }

                            bookingTask.onSuccess(sessions);
                        }
                    }
                });
    }


}
