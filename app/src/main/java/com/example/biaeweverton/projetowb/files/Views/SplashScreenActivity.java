package com.example.biaeweverton.projetowb.files.Views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MyFirebaseMessagingService;
import com.example.biaeweverton.projetowb.files.Models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Calendar;

public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth fbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fbAuth = FirebaseAuth.getInstance();
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogin();
            }
        }, 2000);
    }

    private void showLogin(){
        if(fbAuth.getCurrentUser() != null){
            if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty() || !FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()){
                Account.userId = FirebaseAuth.getInstance().getUid();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        }else{
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
    }


}
