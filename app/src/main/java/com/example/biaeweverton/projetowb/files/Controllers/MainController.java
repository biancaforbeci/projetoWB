package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.biaeweverton.projetowb.files.Class.Deck;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by biafo on 08/12/2018.
 */

public class MainController {

    /*
    * @param context -> context this activity
    * @param rvDeck -> Recycle View to list all result
    * @param idUser -> Find in the database with this id
    * @return void
     */
    public void getDeckList(final Context context, final RecyclerView rvDeck, String idUser){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("deck").whereEqualTo("idUser", idUser).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && task.getResult() != null){
                    ArrayList<Deck> deckList = new ArrayList<>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Deck deck = doc.toObject(Deck.class);
                        deckList.add(deck);
                    }
                    rvDeck.setAdapter(new RecyclerViewDeckAdapter(context, deckList));
                }
            }
        });
    }
}
