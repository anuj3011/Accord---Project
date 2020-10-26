package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class LocationService {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    AsyncHttpClient client = new AsyncHttpClient();

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

    public void getDistance(String origin, String dest, final LocationTask locationTask) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + origin + "&destinations=" + dest + "&key=AIzaSyBzxHNvKBg0F8Kkf6WnMakqcnudVwzcPZs";
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    Log.d("distance", response.toString());
                    locationTask.onGetDistance(response.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                locationTask.onFailure(errorResponse.toString());
            }
        });

    }

    public void pushLocation(final String type, final String uid, final Object location, final LocationTask locationTask) {
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
