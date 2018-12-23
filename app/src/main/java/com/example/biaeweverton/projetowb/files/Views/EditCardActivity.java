package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.EditCardController;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.EditCardInterface;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewListCardAdapter;

import java.util.ArrayList;

public class EditCardActivity extends AppCompatActivity {

    private RecyclerView rvListCard;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        //Initialize
        rvListCard = findViewById(R.id.rvListCard);
        String idDeck = getIntent().getStringExtra("idDeck");
        context = this;

        //RecycleConfig
        rvListCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvListCard.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        EditCardController editCardController = new EditCardController(this);
        editCardController.getListCard(idDeck, new EditCardInterface() {
            @Override
            public void onComplete(ArrayList<Card> listCard) {
                rvListCard.setAdapter(new RecyclerViewListCardAdapter(context, listCard));
            }

            @Override
            public void onCompleteUpdate(Boolean b) {

            }
        });
    }

}
