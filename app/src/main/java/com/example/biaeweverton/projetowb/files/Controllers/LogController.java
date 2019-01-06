package com.example.biaeweverton.projetowb.files.Controllers;

import android.support.annotation.NonNull;


import com.example.biaeweverton.projetowb.files.Models.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
    @Author Weverton Couto
    @Description This class is responsible for sending one or many error to Firestore DataBase. In resume is generated a log.
 */
public class LogController {

    /**
        Default Constructor
     */


    /**
        @Description Save the error in database
        @param firebaseFirestore -> Object FirebaseFirestore
        @param log -> Object Log
        @return void
     */
    public static void shootError(FirebaseFirestore firebaseFirestore, Log log){
        try {
            // Add to object in Database.
            firebaseFirestore.collection("Log").add(log).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                }
            });
        }catch (Exception e){

        }
    }
}
