package com.example.accord.Firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.accord.Models.NGO;
import com.example.accord.Models.ServiceProvider;
import com.example.accord.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
                .document("testUid")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (type.equals("user")) {
                    User test = documentSnapshot.toObject(User.class);
                    userTask.onSuccess(test);
                } else if (type.equals("ngo")) {
                    userTask.onSuccess(documentSnapshot.toObject((NGO.class)));
                } else {
                    userTask.onSuccess(documentSnapshot.toObject(ServiceProvider.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userTask.onFailure(e.getMessage());
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
