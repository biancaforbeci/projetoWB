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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {
    private Context context;
    RegisterController registerController = new RegisterController(context);
    private EditText email;
    private EditText password;
    private EditText numberTyped;
    private EditText number;
    private Button resendButton;
    private FirebaseAuth fbAuth;
    private String phoneVerificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.emailUser);
        password=findViewById(R.id.passwordUser);
        numberTyped=findViewById(R.id.phone);
        number= findViewById(R.id.confirmPassword);
        resendButton = findViewById(R.id.btnResend);
        //numberTyped.addTextChangedListener(MaskEditUtil.mask(numberTyped,MaskEditUtil.FORMAT_FONE));
    }

    public void saveAccount(View view){

        switch (registerController.verification(email.getText().toString(),password.getText().toString(),number.getText().toString(),phoneVerificationID)){
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

            setUpVerificationCallbacks();

            try {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        numberTyped.getText().toString(),
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
                Toast.makeText(RegisterActivity.this,"Enviado com sucesso",Toast.LENGTH_LONG).show();
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

            }
        };
    }

    public void resendSMS(View view) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numberTyped.getText().toString(),        // Phone number to verify
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

                            //add new account here;

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "Falha na autenticação !");
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                number.setError("Código inválido !.");
                                // [END_EXCLUDE]
                            }
                        }
                    }
                });
    }

}
