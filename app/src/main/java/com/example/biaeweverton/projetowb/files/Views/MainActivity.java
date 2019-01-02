package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Models.Account;
import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;
import com.example.biaeweverton.projetowb.files.Models.MainControllerInterface;
import com.example.biaeweverton.projetowb.files.RecycleViews.RecyclerViewDeckAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private RecyclerView rvDeck;
    private Context context;
    private MainController mainController;
    private ImageView imNewDeck;
    private TextView tvMsgNewCard;
    private ProgressBar pbMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        //Initialized Elements
        rvDeck = findViewById(R.id.rvDecks);
        context = this;
        imNewDeck = findViewById(R.id.imNewDeck);
        tvMsgNewCard = findViewById(R.id.tvMsgNewCard);
        pbMainActivity = findViewById(R.id.pbMainActivity);

        //RecycleView Config
        rvDeck.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

        //Class to Control View
        mainController = new MainController(context);

        //Method to get DeckList
        mainController.getDeckList(context,Account.userId, new MainControllerInterface() {
            @Override
            public void onCompleteSave(Boolean res) {

            }

            @Override
            public void onLoadQuantityDataToStudy(int quantity) {

            }

            @Override
            public void onLoadingDeck(ArrayList<Deck> listDeck) {
                if(listDeck.size() == 0){
                    imNewDeck.setVisibility(View.VISIBLE);
                    tvMsgNewCard.setVisibility(View.VISIBLE);
                    rvDeck.setVisibility(View.INVISIBLE);
                }else{
                    imNewDeck.setVisibility(View.INVISIBLE);
                    rvDeck.setVisibility(View.VISIBLE);
                    tvMsgNewCard.setVisibility(View.INVISIBLE);
                    rvDeck.setAdapter(new RecyclerViewDeckAdapter(context, listDeck));
                }
                pbMainActivity.setVisibility(View.INVISIBLE);
            }
        });

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
                if(edNameDeck.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Escreva o nome do Deck", Toast.LENGTH_SHORT).show();
                    return;
                }
                Deck deck = new Deck();
                deck.idUser = Account.userId;
                deck.name = edNameDeck.getText().toString();
                mainController.addNewDeck(deck, new MainControllerInterface() {
                    @Override
                    public void onCompleteSave(Boolean res) {
                        Toast.makeText(MainActivity.this, "Deck Criado!", Toast.LENGTH_SHORT).show();
                        alert.cancel();
                    }

                    @Override
                    public void onLoadQuantityDataToStudy(int quantity) {

                    }

                    @Override
                    public void onLoadingDeck(ArrayList<Deck> listDeck) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                auth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
}
