package com.example.biaeweverton.projetowb.files.Controllers;

import android.support.annotation.NonNull;


import com.example.biaeweverton.projetowb.files.Models.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogController {

    public static void shootError(FirebaseFirestore firebaseFirestore, Log log){
        try {
            firebaseFirestore.collection("Log").add(log).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                }
            });
        }catch (Exception e){

        }
    }
}
