package com.example.biaeweverton.projetowb.files.Controllers;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();
        Notification notification = new Notification();
        notification.setId(s);

        mFire.collection("idMessaging").add(notification).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

            }
        });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("Teste", remoteMessage.getData().toString());
        Log.i("Teste", "Estou aqui");
    }

    public void saveNewIdNotification() {
        final String userId = FirebaseAuth.getInstance().getUid();
        final FirebaseFirestore fire = FirebaseFirestore.getInstance();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull final Task<InstanceIdResult> task) {
                try{
                    if(task.getResult() != null){
                        final Notification notification = new Notification();
                        notification.setId(task.getResult().getToken());
                        notification.setIdUser(userId);

                        fire.collection("Notification").whereEqualTo("idUser", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.getResult() != null){
                                    if(task.getResult().getDocuments().size() == 0){
                                        saveID(fire, notification);
                                    }
                                }
                            }
                        });

                    }
                }catch (Exception e){
                    LogController.shootError(fire, new com.example.biaeweverton.projetowb.files.Models.Log("saveNewIdNotification",new Date(), e.getMessage(), Account.userId));
                }
            }
        });
    }
    private void saveID(FirebaseFirestore fire, Notification notification){
        fire.collection("Notification").add(notification).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

            }
        });
    }
}
