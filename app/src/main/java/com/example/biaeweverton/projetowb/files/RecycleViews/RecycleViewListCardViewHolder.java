package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.biaeweverton.projetowb.R;

class RecycleViewListCardViewHolder extends RecyclerView.ViewHolder {
    public TextView tvFront;
    public TextView tvBack;
    public RecycleViewListCardViewHolder(View view) {
        super(view);
        this.tvBack = view.findViewById(R.id.tvBack);
        this.tvFront = view.findViewById(R.id.tvFront);
    }
}
