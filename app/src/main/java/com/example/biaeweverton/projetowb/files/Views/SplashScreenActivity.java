package com.example.biaeweverton.projetowb.files.Views;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.biaeweverton.projetowb.R;
import com.google.firebase.auth.FirebaseAuth;

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
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        }else{
            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
        }
    }


}
