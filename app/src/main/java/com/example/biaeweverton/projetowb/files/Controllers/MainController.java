package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;

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
    public void getDeckList(final Context context, final RecyclerView rvDeck, String idUser){
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
                    rvDeck.setAdapter(new RecyclerViewDeckAdapter(context, deckList));
                }
            }
        });
    }

    public void addNewDeck(Deck deck){
        this.db.collection("deck").add(deck).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Novo Deck salvo...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteDeck(String idDeck){
        this.db.collection("deck").document(idDeck).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void updateDeck(Deck deck) {
        this.db.collection("deck").document(deck.id).set(deck).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Successful
            }
        });
    }
}
