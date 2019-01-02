package com.example.biaeweverton.projetowb.files.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;
import com.example.biaeweverton.projetowb.files.Controllers.StudyController;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.StudyControllerInterface;

import java.util.ArrayList;

public class StudyActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private String idDeck;
    private TextView tvBack;
    private TextView tvFront;
    private ArrayList<Card> listCards;
    private int currentCard = 0;
    private int originSize = 0;
    private int[] clicks = new int[3];
    private Context context;
    private ProgressBar pbStudy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        //Initialize Vars
        this.frameLayout = findViewById(R.id.fButtons);
        this.tvBack = findViewById(R.id.tvBack);
        this.tvFront = findViewById(R.id.tvFront);
        this.idDeck = getIntent().getStringExtra("idDeck");
        this.context = this;
        this.pbStudy = findViewById(R.id.pbStudy);

        StudyController studyController = new StudyController(this);
        studyController.getAllCards(this.idDeck, new StudyControllerInterface() {
            @Override
            public void onCompleteLoading(ArrayList<Card> listCard) {
                listCards = listCard;
                originSize = listCard.size();
                tvFront.setText(listCards.get(currentCard).getFront());
            }

            @Override
            public void onUpdateComplete(boolean b) {

            }
        });


        //First state
        final View viewShowBack = View.inflate(this,R.layout.fbutton_showback, null);
        BootstrapButton btnShowBack = viewShowBack.findViewById(R.id.btnShowBack);

        final View viewShowRating = View.inflate(this,R.layout.fbutton_showrating, null);
        BootstrapButton btnGood = viewShowRating.findViewById(R.id.btnGood);
        BootstrapButton btnHard = viewShowRating.findViewById(R.id.btnHard);
        BootstrapButton btnEasy = viewShowRating.findViewById(R.id.btnEasy);

        frameLayout.addView(viewShowBack);

        //Button Show Back
        btnShowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBack.setText(listCards.get(currentCard).getBack());
                frameLayout.removeView(viewShowBack);
                frameLayout.addView(viewShowRating);
            }
        });

        //Three Buttons btnHard, btnGood, btnEasy
        btnGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks[1]++;
                if(currentCard == (listCards.size() - 1)){
                    listCards.get(currentCard).setDay(3);
                    Toast.makeText(StudyActivity.this, "Você terminou por hoje.", Toast.LENGTH_SHORT).show();
                    save();
                    return;
                }
                listCards.get(currentCard).setDay(3);
                nextCard();
                frameLayout.removeView(viewShowRating);
                frameLayout.addView(viewShowBack);
            }
        });

        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks[2]++;
                if(currentCard < listCards.size()) listCards.get(currentCard).setDay(1);
                listCards.add(listCards.get(currentCard));
                nextCard();
                frameLayout.removeView(viewShowRating);
                frameLayout.addView(viewShowBack);
            }
        });

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks[0]++;
                if(currentCard == (listCards.size() - 1)){
                    listCards.get(currentCard).setDay(6);
                    Toast.makeText(StudyActivity.this, "Você terminou por hoje.", Toast.LENGTH_SHORT).show();
                    save();
                    return;
                }
                listCards.get(currentCard).setDay(6);
                nextCard();
                frameLayout.removeView(viewShowRating);
                frameLayout.addView(viewShowBack);
            }
        });

    }

    public void nextCard(){
        this.currentCard++;
        tvFront.setText(listCards.get(currentCard).getFront());
        tvBack.setText("");
    }

    public void save(){
        final StudyController studyController = new StudyController(this);
        pbStudy.setVisibility(View.VISIBLE);
        studyController.updateDayCard(this.listCards, new StudyControllerInterface() {
            @Override
            public void onCompleteLoading(ArrayList<Card> listCard) {

            }

            @Override
            public void onUpdateComplete(boolean b) {
                if(b == true){
                    pbStudy.setVisibility(View.INVISIBLE);
                    finish();
                }else{
                    Toast.makeText(context, "Ocorre um erro ao prosseguir.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                final AlertDialog alert = new AlertDialog.Builder(context).create();

                View view = View.inflate(context, R.layout.dialog_infostudy, null);

                TextView tvClickHard = view.findViewById(R.id.tvClickHard);
                TextView tvClickGood = view.findViewById(R.id.tvClickGood);
                TextView tvClickEasy = view.findViewById(R.id.tvClickEasy);

                //Arrumar isso dps
                tvClickEasy.setText("" + clicks[0]);
                tvClickGood.setText("Click's " + clicks[1]);
                tvClickHard.setText("Click's " + clicks[2]);

                alert.setView(view);

                alert.setCancelable(false);

                BootstrapButton btnOk = view.findViewById(R.id.btnOk);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                        finish();
                    }
                });

                //alert.show();
            }
        });
    }
}
