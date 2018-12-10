package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by biafo on 10/12/2018.
 */

public class RegisterController {
    private FirebaseFirestore db;
    private Context context;

    public RegisterController(Context context){
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void addNewAccount(Account email){
        this.db.collection("account").add(email).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Conta criada com sucesso !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int verification(String email, String password){
        if(email.contains("@") == false || password.length() > 8) {
            return 1;
        }else if(verificationSMS() == false){
            return 2;
        }else{
            return 3;
        }
    }

    public boolean verificationSMS(){
        return false;
    }

}
