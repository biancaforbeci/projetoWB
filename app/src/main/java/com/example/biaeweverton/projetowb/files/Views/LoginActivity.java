package com.example.biaeweverton.projetowb.files.Views;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.LoginController;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;

public class LoginActivity extends AppCompatActivity {

    private EditText email;


    //Class controller login
    LoginController loginController= new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public void login(View view) {
        email=findViewById(R.id.email);

        switch (loginController.validation(email.getText().toString().trim())){
            case 1:

                AlertDialog builder= new AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Email em branco.")
                        .setNeutralButton("OK",null)
                        .show();

                break;

            case 2:

                AlertDialog builder1= new AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Verifique se foi digitado um email válido.")
                        .setNeutralButton("OK",null)
                        .show();
                break;

            case 3:

                AlertDialog builder2= new AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Esse email não está cadastrado, registre-se !")
                        .setNeutralButton("OK",null)
                        .show();
                break;

            case 4:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void translatePage(View view) {
        Intent intent = new Intent(LoginActivity.this,TranslateActivity.class);
        startActivity(intent);
    }
}
