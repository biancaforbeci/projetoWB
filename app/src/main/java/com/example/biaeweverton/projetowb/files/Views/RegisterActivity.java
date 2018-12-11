package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.RegisterController;
import com.example.biaeweverton.projetowb.files.Models.Account;

public class RegisterActivity extends AppCompatActivity {
    private Context context;
    RegisterController registerController = new RegisterController(context);
    private EditText email;
    private EditText password;
    private EditText numberTyped;
    private EditText number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.emailUser);
        password=findViewById(R.id.passwordUser);
        numberTyped=findViewById(R.id.phone);
        number= findViewById(R.id.confirmPassword);
    }

    public void saveAccount(View view){

        switch (registerController.verification(email.getText().toString(),password.getText().toString(),numberTyped.getText().toString())){
            case 1:

                AlertDialog builder= new AlertDialog.Builder(this)
                        .setTitle("Erro")
                        .setMessage("Verifique se foi digitado um email válido e se a senha possui até 8 caracteres.")
                        .setNeutralButton("OK",null)
                        .show();

                break;

            case 2:

                AlertDialog builder1= new AlertDialog.Builder(this)
                        .setTitle("Erro de confirmação")
                        .setMessage("Número digitado não corresponde ao enviado por SMS.")
                        .setNeutralButton("OK",null)
                        .show();

                break;

            case 3:
                Account account = new Account();
                account.setEmail(email.getText().toString());
                registerController.addNewAccount(account);

                break;

        }

    }

    public void sendSMS(View view) {
        boolean request =registerController.sendSMS(numberTyped.getText().toString());

        if(request){
            Toast.makeText(RegisterActivity.this,"Enviado com sucesso",Toast.LENGTH_LONG).show();

        }else{
            AlertDialog builder1 = new AlertDialog.Builder(this)
                    .setTitle("Erro de confirmação")
                    .setMessage("Verifique o telefone digitado !")
                    .setNeutralButton("OK",null)
                    .show();
        }
    }
}
