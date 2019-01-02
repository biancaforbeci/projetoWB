package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.Models.Log;
import com.example.biaeweverton.projetowb.files.Models.MainControllerInterface;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by biafo on 08/12/2018.
 */

public class MainController {
    public FirebaseFirestore db;
    private Context context;
    public MainController(Context context){
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }
    /*
    * @param context -> context this activity
    * @param rvDeck -> Recycle View to list all result
    * @param idUser -> Find in the database with this id
    * @return void
     */
    public void getDeckList(final Context context, String idUser, final MainControllerInterface mainControllerInterface){
        try{
            this.db.collection("deck").whereEqualTo("idUser", idUser).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots != null){
                        ArrayList<Deck> deckList = new ArrayList<>();
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            Deck deck = doc.toObject(Deck.class);
                            deck.id = doc.getId();
                            deckList.add(deck);
                        }
                        mainControllerInterface.onLoadingDeck(deckList);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("getDeckList",new Date(), e.getMessage(), Account.userId));
        }

    }

    public void addNewDeck(Deck deck, final MainControllerInterface mainControllerInterface){
        try{
            this.db.collection("deck").add(deck).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if(task.isSuccessful()){
                        mainControllerInterface.onCompleteSave(true);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("addNewDeck",new Date(), e.getMessage(), Account.userId));
        }

    }

    public void deleteDeck(String idDeck){
        try{
            this.db.collection("deck").document(idDeck).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Success
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("deleteDeck",new Date(), e.getMessage(), Account.userId));
        }

    }

    public void updateDeck(Deck deck) {
        try{
            this.db.collection("deck").document(deck.id).set(deck).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //Success
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("updateDeck",new Date(), e.getMessage(), Account.userId));
        }

    }

    public void addItemDeck(Card card, final MainControllerInterface mainControllerInterface){
        try{
            this.db.collection("card").add(card).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    mainControllerInterface.onCompleteSave(true);
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("addItemDeck",new Date(), e.getMessage(), Account.userId));
        }

    }

    public void getQuantityDataToStudy(String idDeck, final MainControllerInterface mainControllerInterface){
        try{
            this.db.collection("card").whereEqualTo("idDeck", idDeck).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots != null) mainControllerInterface.onLoadQuantityDataToStudy(queryDocumentSnapshots.size());
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("getQuantityDataToStudy",new Date(), e.getMessage(), Account.userId));
        }

    }
}
