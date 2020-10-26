package com.example.accord.Firestore;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class LocationService {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public interface LocationTask {


        void onGetDistance(String value);

        void onFailure(String msg);

        void onSuccess(Object location);
    }

    Object parseLocationFromSnapshot(DocumentSnapshot documentSnapshot) {
        Map loc = documentSnapshot.getData();
        String location = (String) loc.get("location");
        return location;
    }

  public   void pushLocation(final String type, final String uid, final Object location, final LocationTask locationTask) {
        final Map<String, Object> loc = new HashMap();
        loc.put("location", location);
        firebaseFirestore.collection(type).document(uid).set(loc, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                locationTask.onSuccess(location);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseFirestore.collection(type).document(uid).set(loc, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        locationTask.onSuccess(location);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        try {
                            throw e;
                        } catch (Exception error) {
                            locationTask.onFailure(error.getMessage());
                        }
                    }
                });
            }
        });
    }

   public void getLocation(String type, String uid, final LocationTask locationTask) {
        firebaseFirestore.collection(type).document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String location= (String) parseLocationFromSnapshot(documentSnapshot);
                locationTask.onSuccess(location);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    throw e;
                } catch (Exception error) {
                    locationTask.onFailure(error.getMessage());
                }
            }
        });
    }
}
