package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import  com.google.android.gms.maps.GoogleMap;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class LocationService {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    Thread realTimeLocation;


    public interface LocationTask {
        // callback functions

        void onGetDistance(String value);

        void onGetServiceProvidersWithinDistance(List<ServiceProvider> serviceProviders);

        void onFailure(String msg);

        void onSuccess(Object location);
    }


    public double getDistance(CustomLatLng origin, CustomLatLng dest) {
        LatLng originLoc=new LatLng(origin.getLatitude(),origin.getLongitude());
        LatLng destLoc=new LatLng(dest.getLatitude(),dest.getLongitude());
        return 1;
      //  return SphericalUtil.computeDistanceBetween(originLoc, destLoc);

    }

    public void getServiceProvidersWithinDistance(final User user, final double distance, final LocationTask locationTask) {
        firebaseFirestore.collection("sp").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ServiceProvider serviceProvider = documentSnapshot.toObject(ServiceProvider.class);
                    if (getDistance(user.currentLocation, serviceProvider.currentLocation) < distance) {
                        serviceProviders.add(serviceProvider);
                    }
                }
                locationTask.onGetServiceProvidersWithinDistance(serviceProviders);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                locationTask.onFailure(e.getMessage());
            }
        });
    }

    void pushLocation(final String type, final String uid, final Object location, final LocationTask locationTask) {
        final Map<String, Object> loc = new HashMap();
        loc.put("currentLocation", location);
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


                pushLocation(type, uid, location, locationTask);


            }
        };
        realTimeLocation.start();// starts a loop location write after every 10 seconds on a different thread


    }

}
