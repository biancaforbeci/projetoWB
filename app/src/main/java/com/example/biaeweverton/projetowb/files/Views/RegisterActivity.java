package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.Masks.MaskEditUtil;
import com.example.biaeweverton.projetowb.files.Controllers.RegisterController;
import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {
    private Context context;
    private EditText email;
    private EditText numberTyped;
    private EditText number;
    private boolean SMSTyped;
    RegisterController registerController;
    private FirebaseAuth fbAuth;
    private String phoneVerificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        numberTyped=findViewById(R.id.phone);
        number= findViewById(R.id.confirmCode);
        registerController = new RegisterController(this);
        fbAuth = FirebaseAuth.getInstance();

        final BootstrapButton btnSend = (BootstrapButton) findViewById(R.id.btnSendSMS);
        final BootstrapButton btnResend = (BootstrapButton) findViewById(R.id.resend);
        final BootstrapButton btnRegister = (BootstrapButton) findViewById(R.id.btnLogin);


        btnResend.setEnabled(false);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendSMS();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(number.getText().toString());
            }
        });


    }


    private void sendSMS() {

            setUpVerificationCallbacks();

            try {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+55"+numberTyped.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        this,
                        verificationCallbacks);
            }catch (Exception e){
                AlertDialog builder1= new AlertDialog.Builder(this)
                        .setTitle("Erro ao enviar SMS")
                        .setMessage("Verifique o número digitado e tente novamente !")
                        .setNeutralButton("OK",null)
                        .show();
            }

    }

    private void setUpVerificationCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if(e instanceof FirebaseAuthInvalidCredentialsException){  //invalid number
                    Log.d(TAG,"Número inválido");
                }else if(e instanceof FirebaseTooManyRequestsException){  //many request
                    Log.d(TAG,"Excesso de requisições.");
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token, use them later
                phoneVerificationID=verificationId;
                resendToken = token;
                final BootstrapButton btnResend = (BootstrapButton) findViewById(R.id.resend);
                btnResend.setEnabled(true);
                Toast.makeText(RegisterActivity.this,"Enviado com sucesso",Toast.LENGTH_LONG).show();
            }
        };
    }

    private void resendSMS() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+55"+numberTyped.getText().toString(),        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks,         // OnVerificationStateChangedCallbacks
                resendToken);             // ForceResendingToken from callbacks
    }


    public void signInWithCredential(PhoneAuthCredential credential){
         fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Sucesso");
                           SMSTyped=true;
                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "Falha na autenticação !");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                number.setError("Código inválido !");
                            }
                        }
                    }
                });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationID,code);
        signInWithCredential(credential);
    }

}
