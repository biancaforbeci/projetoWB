package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Views.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * Created by biafo on 10/12/2018.
 */

public class RegisterController {
    private FirebaseFirestore db;
    private FirebaseAuth fbAuth;
    private Context context;
    private String phoneVerificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;


    public RegisterController(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void addNewAccount(Account email) {
        this.db.collection("account").add(email).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Conta criada com sucesso !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int verification(String email, String password, String numberTyped) {
        if (LoginController.isEmailValid(email) == false || password.length() > 8) {
            return 1;
        } else if (validationPhone(numberTyped) == false) {
            return 2;
        } else {
            return 3;
        }
    }

    public boolean sendSMS(String phone) {
        setUpVerificationCallbacks();

        try {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    (Executor) this,
                    verificationCallbacks);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void setUpVerificationCallbacks() {
        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                if(e instanceof  FirebaseAuthInvalidCredentialsException){
                    Log.d(TAG,"Erro ao enviar SMS.");
                }else if(e instanceof  FirebaseTooManyRequestsException){
                    Log.d(TAG,"Excesso de requisições.");
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationID=verificationId;
                resendToken = token;

            }
        };
    }

    public boolean validationPhone(String number) {
        //here
       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationID,number);
       return true;
    }





}


