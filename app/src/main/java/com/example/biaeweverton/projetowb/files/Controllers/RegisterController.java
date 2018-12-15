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
import com.google.firebase.auth.AuthResult;
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
    private Context context;
    private String phoneVerificationID;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;


    public RegisterController(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public RegisterController(){

    }

    public void addNewAccount(String email) {
        Account user = new Account();
        user.email=email;


        this.db.collection("account").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Conta criada com sucesso !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int verification(String email, String numberTyped,String phoneVerificationID) {
        if (LoginController.isEmailValid(email) == false){
            return 1;
        } else {
            return 3;
        }
    }

    public PhoneAuthCredential validationPhone(String number,String phoneVerificationID) {

       PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationID,number);

       return credential;
    }





}


