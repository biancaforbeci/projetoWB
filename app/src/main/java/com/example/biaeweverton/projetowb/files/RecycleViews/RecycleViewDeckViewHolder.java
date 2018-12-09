package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.biaeweverton.projetowb.R;

class RecycleViewDeckViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameDeck;
    public TextView tvStudyToday;
    public RecycleViewDeckViewHolder(View view) {
        super(view);
        this.tvNameDeck = view.findViewById(R.id.tvNameDeck);
        this.tvStudyToday = view.findViewById(R.id.tvStudyToday);
    }
}
