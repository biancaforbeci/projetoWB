package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;

class RecycleViewDeckViewHolder extends RecyclerView.ViewHolder {
    public TextView tvNameDeck;
    public TextView tvStudyToday;
    public BootstrapButton btnDeleteDeck;
    public BootstrapButton btnEditDeck;
    public BootstrapButton btnAddItem;
    public BootstrapButton btnStudyNow;
    public RecycleViewDeckViewHolder(View view) {
        super(view);
        this.tvNameDeck = view.findViewById(R.id.tvNameDeck);
        this.tvStudyToday = view.findViewById(R.id.tvStudyToday);
        this.btnDeleteDeck = view.findViewById(R.id.btnDeleteDeck);
        this.btnEditDeck = view.findViewById(R.id.btnEditDeck);
        this.btnAddItem = view.findViewById(R.id.btnAddItem);
        this.btnStudyNow = view.findViewById(R.id.btnStudyNow);
    }
}
