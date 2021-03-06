package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.EditCardController;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.EditCardInterface;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewListCardAdapter;

import java.util.ArrayList;

public class EditCardActivity extends AppCompatActivity {

    private RecyclerView rvListCard;
    private Context context;
    private ProgressBar pbEditCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        //Initialize
        rvListCard = findViewById(R.id.rvListCard);
        String idDeck = getIntent().getStringExtra("idDeck");
        pbEditCard = findViewById(R.id.pbEditCard);
        context = this;

        //RecycleConfig
        rvListCard.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        EditCardController editCardController = new EditCardController(this);
        editCardController.getListCard(idDeck, new EditCardInterface() {
            @Override
            public void onComplete(ArrayList<Card> listCard) {
                rvListCard.setAdapter(new RecyclerViewListCardAdapter(context, listCard));
                pbEditCard.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCompleteUpdate(Boolean b) {

            }
        });
    }

}
