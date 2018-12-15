package com.example.biaeweverton.projetowb.files.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        //Initialize Vars
        this.frameLayout = findViewById(R.id.fButtons);
        this.tvBack = findViewById(R.id.tvBack);
        this.tvFront = findViewById(R.id.tvFront);
        this.idDeck = getIntent().getStringExtra("idDeck");

        StudyController studyController = new StudyController(this);
        studyController.getAllCards(this.idDeck, new StudyControllerInterface() {
            @Override
            public void onCompleteLoading(ArrayList<Card> listCard) {
                listCards = listCard;
                tvFront.setText(listCards.get(currentCard).getFront());
            }
        });


        //First state
        final View viewShowBack = View.inflate(this,R.layout.fbutton_showback, null);
        BootstrapButton btnShowBack = viewShowBack.findViewById(R.id.btnShowBack);

        final View viewShowRating = View.inflate(this,R.layout.fbutton_showrating, null);
        BootstrapButton btnGood = viewShowRating.findViewById(R.id.btnGood);

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
                if(currentCard == (listCards.size() - 1)){
                    Toast.makeText(StudyActivity.this, "Opa não tem mais", Toast.LENGTH_SHORT).show();
                    return;
                }
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
}
