package com.example.biaeweverton.projetowb.files.Models;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.biaeweverton.projetowb.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void login(View view) {
    }

    public void register(View view) {
    }
}
