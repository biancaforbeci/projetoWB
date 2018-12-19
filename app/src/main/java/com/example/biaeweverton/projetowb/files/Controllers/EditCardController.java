package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.EditCardInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditCardController {
    private FirebaseFirestore db;
    private Context context;

    public EditCardController(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    public void getListCard(String idDeck, final EditCardInterface editCardInterface){
        this.db.collection("card").whereEqualTo("idDeck", idDeck).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    ArrayList<Card> listCard = new ArrayList<>();
                    for(DocumentSnapshot docs : task.getResult()) {
                        listCard.add(docs.toObject(Card.class));
                    }
                    editCardInterface.onComplete(listCard);
                }
            }
        });
    }
}
