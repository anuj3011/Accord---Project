package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.accord.Models.CustomLatLng;
import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.android.gms.maps.GoogleMap;


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
        LatLng originLoc = new LatLng(origin.getLatitude(), origin.getLongitude());
        LatLng destLoc = new LatLng(dest.getLatitude(), dest.getLongitude());
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

    public void pushLocation(final String type, final String uid, final Object location, final LocationTask locationTask) {
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

    public void getLocationOfActiveUser(final String type, String uid,
                                        final LocationService.LocationTask locationTask) {
        firebaseFirestore.collection(type).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try {
                    if (error != null) {
                        throw error;
                    }
                    CustomLatLng latLng = new CustomLatLng();
                    if (type.equals("user")) {
                        latLng = value.toObject(User.class).currentLocation;

                    } else if (type.equals("sp")) {
                        latLng = value.toObject(ServiceProvider.class).currentLocation;

                    } else if (type.equals("ngo")) {
                        latLng = value.toObject(NGO.class).currentLocation;

                    }
                    locationTask.onSuccess(latLng);

                } catch (Exception e) {
                    locationTask.onFailure(e.getMessage());
                }
            }
        });
    }


}
