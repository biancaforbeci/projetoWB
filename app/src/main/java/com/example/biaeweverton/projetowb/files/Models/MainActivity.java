package com.example.biaeweverton.projetowb.files.Models;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvDeck;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialized Elements
        rvDeck = findViewById(R.id.rvDecks);
        context = this;

        //RecycleView Config
        rvDeck.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        //Class to Control View
        MainController mainController = new MainController();

        //Method to get DeckList
        mainController.getDeckList(context,rvDeck, "Ry0QC6OMFMxYtU5Ui5jM");

    }
}
