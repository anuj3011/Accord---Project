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

        void onSuccess(Session session);

        void onSuccess();

        void onFailed(String msg);
    }

    public void endService(final String sessionID, final String serviceProviderID, final BookingTask callback) {
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
                    setServiceProviderActive(serviceProviderID,sessionID,false,callback);
                    callback.onSuccess();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailed(e.getMessage());
                    }
                }
            }
        });
    }


    public void bookService(String userID, String category, boolean isSkilled, final onBooked onBookedCallBack) {
        session.userID = userID;
        session.isActive = true;
        session.isCompleted = false;
        session.isSearchStarted = true;
        session.serviceCategory = category;
        session.isServiceSkilled = isSkilled;
        session.serviceCategory = category;
        DocumentReference sessionReference = db.collection("sessions").document();
        session.sessionID = sessionReference.getId();
        sessionReference.set(session).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                onBookedCallBack.onBooked(session);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onBookedCallBack.onBookingFailed();
            }
        });
    }

    public void cancelSession(final String sessionID, final String serviceProviderID, final BookingTask bookingTask) {
        setServiceProviderActive(serviceProviderID,"",false
                ,bookingTask);
        Map data=new HashMap();
        data.put("isActive",false);
        data.put("isCompleted",false);
        db.collection("sessions").document(sessionID).set(data,SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                            setServiceProviderActive(serviceProviderID,sessionID,false,bookingTask);
                        bookingTask.onSuccess();// accepted
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bookingTask.onFailed(e.getMessage());
            }
        });
    }

    public void setServiceProviderActive(String serviceProviderID, final String sessionID, boolean isActive, final BookingTask bookingTask) {
        Map<String, Object> map = new HashMap<>();
        if (isActive) {
            map.put("currentSession", sessionID);
        } else {
            map.put("currentSession", "");

        }

        map.put("isActive", isActive);
        db.collection("sp").document(serviceProviderID).set(map, SetOptions.merge())
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

    public void acceptServiceSession(final String serviceProviderID, final String sessionID, final BookingTask bookingTask) {
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
                        setServiceProviderActive(serviceProviderID,sessionID,true,bookingTask);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bookingTask.onFailed(e.getMessage());
            }
        });
    }

    public void checkIfSessionAccepted(String sessionID, final BookingTask bookingTask) {
        db.collection("sessions").document(sessionID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    Session session = value.toObject(Session.class);
                    if (session != null) {
                        if (session.isAccepted) {
                            bookingTask.onSuccess(session);
                        }
                    }

                } else {
                    bookingTask.onFailed(error.getMessage());
                }
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
