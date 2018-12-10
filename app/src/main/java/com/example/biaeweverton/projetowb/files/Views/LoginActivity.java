package com.example.biaeweverton.projetowb.files.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.biaeweverton.projetowb.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void login(View view) {
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        validation(email.getText().toString(),password.getText().toString());
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    private void validation(String email, String password){

    }
}
