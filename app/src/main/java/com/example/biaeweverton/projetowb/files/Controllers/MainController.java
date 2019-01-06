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
 * @Author Weverton Couto
 * @Description This class is responsible for sending one or many error to Firestore DataBase. In resume is generated a log.
 */

public class MainController {
    public FirebaseFirestore db;
    private Context context;

    /**
     * Constructor
     * @param context
     */
    public MainController(Context context){
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /**
     * @Description  get deck list of a user
     * @param context -> context this activity
     * @param idUser -> Find in the database with this id
     * @param mainControllerInterface -> Interface MainControllerInterface
     * @return void
     */
    public void getDeckList(final Context context, String idUser, final MainControllerInterface mainControllerInterface) {
        try {
            // Where condition 'idUser' == idUser
            this.db.collection("deck").whereEqualTo("idUser", idUser).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (queryDocumentSnapshots != null) {
                        ArrayList<Deck> deckList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Deck deck = doc.toObject(Deck.class);
                            deck.id = doc.getId();
                            deckList.add(deck);
                        }
                        mainControllerInterface.onLoadingDeck(deckList);
                    }
                }
            });
        } catch (Exception e) {
            LogController.shootError(this.db, new Log("getDeckList", new Date(), e.getMessage(), Account.userId));
        }

    }

    /**
     * @Description Add new deck in the database
     * @param deck -> Object deck
     * @param mainControllerInterface -> Interface MainControllerInterface
     * @return void
     */
    public void addNewDeck(Deck deck, final MainControllerInterface mainControllerInterface){
        try{
            // Add new objec deck in the database
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

    /**
     * @Description Delete deck in the database
     * @param idDeck -> String containing idDeck
     * @return void
     */
    public void deleteDeck(String idDeck){
        try{
            // Delete object Deck in the database
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

    /**
     * @Description Update deck in the database
     * @param deck -> Object Deck
     * @return void
     */
    public void updateDeck(Deck deck) {
        try{
            // Set update in the object that is in database
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

    /**
     * @Description Add new Card in the Deck
     * @param card -> Object Card
     * @param mainControllerInterface -> Interface MainControllerInterface
     * @return void
     */
    public void addItemDeck(Card card, final MainControllerInterface mainControllerInterface){
        try{
            // Add new object Card in the database
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

    /**
     * @Description Get Quantity of Card to Study
     * @param idDeck -> String containing idDeck
     * @param mainControllerInterface -> Interface MainControllerInterface
     * @return void
     */
    public void getQuantityDataToStudy(String idDeck, final MainControllerInterface mainControllerInterface){
        try{
            this.db.collection("card").whereEqualTo("idDeck", idDeck).whereEqualTo("day", 1).addSnapshotListener(new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots != null){ mainControllerInterface.onLoadQuantityDataToStudy(queryDocumentSnapshots.size()); } else { mainControllerInterface.onLoadQuantityDataToStudy(0);}
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new Log("getQuantityDataToStudy",new Date(), e.getMessage(), Account.userId));
        }

    }
}
