package com.example.biaeweverton.projetowb.files.Controllers;

import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    public LoginController(){
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
