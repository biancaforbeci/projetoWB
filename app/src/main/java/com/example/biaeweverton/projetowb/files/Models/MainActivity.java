package com.example.biaeweverton.projetowb.files.Models;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Class.Deck;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvDeck;
    private Context context;
    private MainController mainController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialized Elements
        rvDeck = findViewById(R.id.rvDecks);
        context = this;

        //RecycleView Config
        rvDeck.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

        //Class to Control View
        mainController = new MainController();

        //Method to get DeckList
        mainController.getDeckList(context,rvDeck, "Ry0QC6OMFMxYtU5Ui5jM");

    }

    public void openDialog(View view) {
        final AlertDialog alert = new AlertDialog.Builder(this.context).create();
        View v = View.inflate(this.context, R.layout.dialog_newdeck, null);
        //Initialized Element's
        BootstrapButton btnNewDeck = v.findViewById(R.id.bntNewDeck);
        BootstrapButton btnCancel = v.findViewById(R.id.btnCancel);
        final BootstrapEditText edNameDeck = v.findViewById(R.id.edNameDeck);

        //Set view and show Alert
        alert.setView(v);
        alert.show();

        //Button's Alert
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
        btnNewDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Deck deck = new Deck();
                deck.idUser = "Ry0QC6OMFMxYtU5Ui5jM";
                deck.name = edNameDeck.getText().toString();
                mainController.addNewDeck(deck);
            }
        });
    }
}
