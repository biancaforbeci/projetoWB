package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.EditCardController;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.EditCardInterface;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        RecycleViewListCardViewHolder view = (RecycleViewListCardViewHolder) viewHolder;

        view.tvFront.setText(listCard.get(i).getFront());
        view.tvBack.setText(listCard.get(i).getBack());

        view.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                View view = View.inflate(context, R.layout.dialog_additemdeck, null);

                BootstrapButton btnAddItem = view.findViewById(R.id.btnAddItem);
                BootstrapButton btnCancel = view.findViewById(R.id.btnCancel);
                final TextView edFront = view.findViewById(R.id.edPhrase);
                final TextView edBack = view.findViewById(R.id.edTranslate);

                edBack.setText(listCard.get(i).getBack());
                edFront.setText(listCard.get(i).getFront());
                btnAddItem.setText("Salvar");

                alertDialog.setView(view);

                alertDialog.show();

                btnAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditCardController editCardController = new EditCardController(context);
                        listCard.get(i).setBack(edBack.getText().toString());
                        listCard.get(i).setFront(edFront.getText().toString());
                        editCardController.updateCard(listCard.get(i), new EditCardInterface() {
                            @Override
                            public void onComplete(ArrayList<Card> listCard) {

                            }

                            @Override
                            public void onCompleteUpdate(Boolean b) {
                                if(b){
                                    Toast.makeText(context, "Atualizado", Toast.LENGTH_SHORT).show();
                                    alertDialog.cancel();
                                }
                            }
                        });
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        view.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCardController editCardController = new EditCardController(context);
                editCardController.deleteCard(listCard.get(i), new EditCardInterface() {
                    @Override
                    public void onComplete(ArrayList<Card> listCard) {

                    }

                    //Using same method
                    @Override
                    public void onCompleteUpdate(Boolean b) {
                        if(b){
                            Toast.makeText(context, "Apagado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCard.size();
    }
}
