package com.example.accord.Firestore;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class StorageAPI {
    StorageReference origin = FirebaseStorage.getInstance().getReference();

    public interface StorageTask {
        void onSuccess(Uri url);

        void trackProgress(double val);

        void onFailure();
    }

    public void uploadFile(String uid, final Uri file, final StorageTask storageTask) {
        origin.child(uid + "/profile/image").putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageTask.onSuccess(file);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    throw e;
                } catch (FirebaseNetworkException networkException) {
                    //toast no internet
                } catch (FirebaseException firebaseException) {
                    // generic firebase exception
                } catch (Exception error) {
                    storageTask.onFailure();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                storageTask.trackProgress(taskSnapshot.getBytesTransferred());
            }
        });
    }


}
