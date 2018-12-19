package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Models.Card;

import java.util.ArrayList;

public class RecyclerViewListCardAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Card> listCard;
    public RecyclerViewListCardAdapter(Context context, ArrayList<Card> listCard) {
        this.context = context;
        this.listCard = listCard;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_editcard, viewGroup, false);
        return new RecycleViewListCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RecycleViewListCardViewHolder view = (RecycleViewListCardViewHolder) viewHolder;

        view.tvFront.setText(listCard.get(i).getFront());
        view.tvBack.setText(listCard.get(i).getBack());
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }
}
