package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;
import com.example.biaeweverton.projetowb.files.Models.Deck;

import java.util.ArrayList;

public class RecyclerViewDeckAdapter extends RecyclerView.Adapter {
    private ArrayList<Deck> deckList;
    private Context context;
    public RecyclerViewDeckAdapter(Context context, ArrayList<Deck> deckList) {
        this.deckList = deckList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mydecks, viewGroup, false);
        return new RecycleViewDeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        RecycleViewDeckViewHolder view = (RecycleViewDeckViewHolder) viewHolder;
        final Deck deck = this.deckList.get(i);
        view.tvNameDeck.setText(deck.name);
        String tvStudy = "Estudar hoje: " + deck.studyToday;
        view.tvStudyToday.setText(tvStudy);

        //Button
        view.btnDeleteDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainController mainController = new MainController(context);
                mainController.deleteDeck(deck.id);
            }
        });

        view.btnEditDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alert = new AlertDialog.Builder(context).create();
                final View v = View.inflate(context, R.layout.dialog_editdeck, null);
                // Initialize Element's
                final BootstrapEditText edNameDeck = v.findViewById(R.id.edNameDeck);
                final BootstrapButton btnToggle = v.findViewById(R.id.btnToggle);
                BootstrapButton btnCancel = v.findViewById(R.id.btnCancel);

                edNameDeck.setText(deckList.get(i).name);

                alert.setView(v);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.cancel();
                    }
                });

                btnToggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainController main = new MainController(context);
                        deckList.get(i).name = edNameDeck.getText().toString();
                        main.updateDeck(deckList.get(i));
                        alert.cancel();
                    }
                });

                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }
}
