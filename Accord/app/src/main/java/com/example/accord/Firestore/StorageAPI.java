package com.example.accord.Firestore;

import android.net.Uri;
import android.util.Log;

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

        void onFailure(String error);
    }

    public void uploadFile(String uid,String path, final Uri file, final StorageTask storageTask) {
        origin.child(uid + "/"+path).putFile(file).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                storageTask.trackProgress(taskSnapshot.getBytesTransferred());
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d("progress", "completed");

                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                storageTask.onSuccess(uri);
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception error) {
                try{
                    throw error;
                }
                catch (FirebaseNetworkException firebaseNetworkException){
                    storageTask.onFailure("No Internet");
                }
                catch (Exception e){

                }
            }
        });
    }


}
