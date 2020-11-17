package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.accord.Models.CustomUser;
import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserAPI {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserAPI() {
        db = FirebaseFirestore.getInstance();
    }

    public interface UserTask {
        void onSuccess(Object object);

        void onFailure(String msg);
    }

    public void getUser(final String type, String uid, final UserTask userTask) {
       db.collection(type)// collection reference
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if(error==null){
                            if (type.equals("user")) {
                                User user = documentSnapshot.toObject(User.class);
                                if(user!=null){
                                    userTask.onSuccess(user);
                                }
                                else {
                                    userTask.onFailure("Could not get User");
                                }

                            } else if (type.equals("sp")) {

                                ServiceProvider user = documentSnapshot.toObject(ServiceProvider.class);
                                if(user!=null){
                                    userTask.onSuccess(user);
                                }
                                else {
                                    userTask.onFailure("Could not get User");
                                }

                            } else {
                                NGO user = documentSnapshot.toObject(NGO.class);
                                if(user!=null){
                                    userTask.onSuccess(user);
                                }
                                else {
                                    userTask.onFailure("Could not get User");
                                }
                            }
                        }
                        else{
                            userTask.onFailure(error.getMessage());
                        }
                    }
                });

    }


    public void pushUser(String type, String uid, final Object object, final UserTask userTask) {
        // Add a new document with a generated ID

        db.collection(type)
                .document(uid)
                .set(object,SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userTask.onSuccess(object);
                    Log.d("document", "added");
                } else {
                    userTask.onFailure(task.getException().getMessage());
                    Log.d("document", task.getException().getMessage());
                }
            }
        });
    }
}
