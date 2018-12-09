package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Class.Deck;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RecycleViewDeckViewHolder view = (RecycleViewDeckViewHolder) viewHolder;
        Deck deck = this.deckList.get(i);
        view.tvNameDeck.setText(deck.name);
        String tvStudy = "Estudar hoje: " + deck.studyToday;
        view.tvStudyToday.setText(tvStudy);
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }
}
