package com.example.biaeweverton.projetowb.files.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.StudyControllerInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
/**
 * @Author Weverton Couto
 * @Description This class is responsible for obtaining data for StudyActivity
 */
public class StudyController {
    private FirebaseFirestore db;
    private Context context;

    /**
     * Constructor
     * @param context -> Object Context
     */
    public StudyController(Context context){
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /**
     * @Description  get All Card with right condition
     * @param idDeck -> String containing idDeck
     * @param studyControllerInterface -> Interface StudyControllerInterface
     */
    public void getAllCards(String idDeck, final StudyControllerInterface studyControllerInterface){
        try{
            // Where Condition 'idDeck' == idDeck AND 'day' == 1
            this.db.collection("card").whereEqualTo("idDeck", idDeck).whereEqualTo("day", 1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful() && task.getResult() != null){
                        ArrayList<Card> listCard = new ArrayList<>();

                        for(DocumentSnapshot item : task.getResult()){
                            Card card = item.toObject(Card.class);
                            if(card != null) card.setId(item.getId());
                            listCard.add(card);
                        }

                        studyControllerInterface.onCompleteLoading(listCard);
                    }
                }
            });
        }catch(Exception e){
            LogController.shootError(this.db, new com.example.biaeweverton.projetowb.files.Models.Log("getAllCards",new Date(), e.getMessage(), Account.userId));
        }

    }

    /**
     * @Description  Update var day when is finish
     * @param list -> Object ArrayList<Card>
     * @param studyControllerInterface -> Interface StudyControllerInterface
     */
    public void updateDayCard(final ArrayList<Card> list, final StudyControllerInterface studyControllerInterface){
        try{
            if(list.size() == 0){
                studyControllerInterface.onUpdateComplete(true);
                return;
            }
            this.db.collection("card").document(list.get(0).getId()).set(list.get(0)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        list.remove(list.get(0));
                        updateDayCard(list, studyControllerInterface);
                    }
                }
            });
        }catch(Exception e){
            studyControllerInterface.onUpdateComplete(false);
            LogController.shootError(this.db, new com.example.biaeweverton.projetowb.files.Models.Log("updateDayCard",new Date(), e.getMessage(), Account.userId));
        }

    }
}
