package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class LocationService {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    AsyncHttpClient client = new AsyncHttpClient();
    Thread realTimeLocation;


    public interface LocationTask {
        // callback functions

        void onGetDistance(String value);

        void onFailure(String msg);

        void onSuccess(Object location);
    }

    Object parseLocationFromSnapshot(DocumentSnapshot documentSnapshot) {
        Map loc = documentSnapshot.getData();
        String location = (String) loc.get("location");
        return location;
    }

    public void getDistance(String origin, String dest, final LocationTask locationTask) {

    }

    void pushLocation(final String type, final String uid, final Object location, final LocationTask locationTask) {
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


                locationTask.onFailure(e.getMessage());
            }
        });
    }

    public void startRealTimeLocationThread(final String type, final String uid, final Object location, final LocationTask locationTask) {

        realTimeLocation = new Thread() {
            @Override
            public void run() {

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        pushLocation(type,uid,location,locationTask);
                    }
                }, 0, 10000);



            }
        };
        realTimeLocation.start();// starts a loop location write after every 10 seconds on a different thread


    }

    public void getLocation(String type, String uid, final LocationTask locationTask) {
        firebaseFirestore.collection(type).document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String location = (String) parseLocationFromSnapshot(documentSnapshot);
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
