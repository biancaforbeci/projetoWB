package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Log;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.EditCardInterface;
import com.example.biaeweverton.projetowb.files.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

/**
    @Author Weverton Couto
    @Description This class is responsible for handling data in the EditCardActivity
 */
public class EditCardController {
    private FirebaseFirestore db;
    private Context context;

    /**
        Constructor
     */
    public EditCardController(Context context){
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /**
        @Description get all cards from specific deck
        @param idDeck -> id do deck
        @param editCardInterface -> Interface EditCardInterface
        @return void
     */
    public void getListCard(String idDeck, final EditCardInterface editCardInterface){
        try{
            // Where 'idDeck' == idDeck
            this.db.collection("card").whereEqualTo("idDeck", idDeck).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots != null) {
                        ArrayList<Card> listCard = new ArrayList<>();
                        for(DocumentSnapshot docs : queryDocumentSnapshots){
                            Card card = docs.toObject(Card.class);
                            if(card != null) {
                                card.setId(docs.getId());
                                listCard.add(card);
                            }
                        }
                        editCardInterface.onComplete(listCard);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("getListCard",new Date(), e.getMessage(), Account.userId));
        }
    }

    /**
        @Description update specific Card
        @param card -> Object Card
        @param editCardInterface -> Interface EditCardInterface
        @return void
     */
    public void updateCard(Card card, final EditCardInterface editCardInterface){
        try{
            // Update Card passing your document id
            this.db.collection("card").document(card.getId()).set(card).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        editCardInterface.onCompleteUpdate(true);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("updateCard",new Date(), e.getMessage(), Account.userId));
        }

    }

    /**
        @Description delete card
        @param card -> Object Card
        @param editCardInterface -> Interface EditCardInterface
        @return void
     */
    public void deleteCard(Card card, final EditCardInterface editCardInterface){
        try {
            // Delete card passing your collection id
            this.db.collection("card").document(card.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        editCardInterface.onCompleteUpdate(true);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("deleteCard",new Date(), e.getMessage(), Account.userId));
        }

    }
}
