package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.StudyControllerInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudyController {
    private FirebaseFirestore db;
    private Context context;

    public StudyController(Context context){
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void getAllCards(String idDeck, final StudyControllerInterface studyControllerInterface){
        this.db.collection("card").whereEqualTo("idDeck", idDeck).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful() && task.getResult() != null){
                    ArrayList<Card> listCard = new ArrayList<>();

                    for(DocumentSnapshot item : task.getResult()){
                        Card card = item.toObject(Card.class);
                        listCard.add(card);
                    }

                    studyControllerInterface.onCompleteLoading(listCard);
                }
            }
        });
    }
}
