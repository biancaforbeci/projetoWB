package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.example.biaeweverton.projetowb.files.Views.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Content;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Created by biafo on 08/12/2018.
 */

public class LoginController {
    private FirebaseFirestore db;
    private boolean request;
    private FirebaseAuth auth;

    public LoginController(){
        this.auth = FirebaseAuth.getInstance();
        this.db=FirebaseFirestore.getInstance();
    }


    public int validation(String email){
        if(email.isEmpty()){
            return  1;
        }else if(isEmailValid(email) == false) {
            return 2;
        }else if(isEmailExist(email.trim())){
            return 3;
        }else{
            return 4;
        }

        //missing return validation database email
    }


    public static boolean isEmailValid(String email) {

        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public boolean isEmailExist(String email){
        this.db.collection("account").whereEqualTo("email", email).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(queryDocumentSnapshots != null){
                    request=true;
                }else{
                    request= false;
                }
            }
        });

        return request;
    }
}
