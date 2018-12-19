package com.example.biaeweverton.projetowb.files.RecycleViews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.biaeweverton.projetowb.R;
import com.example.biaeweverton.projetowb.files.Controllers.MainController;
import com.example.biaeweverton.projetowb.files.Models.Card;
import com.example.biaeweverton.projetowb.files.Models.Deck;
import com.example.biaeweverton.projetowb.files.Models.MainControllerInterface;
import com.example.biaeweverton.projetowb.files.Views.EditCardActivity;
import com.example.biaeweverton.projetowb.files.Views.StudyActivity;

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
        final RecycleViewDeckViewHolder view = (RecycleViewDeckViewHolder) viewHolder;
        final Deck deck = this.deckList.get(i);
        view.tvNameDeck.setText(deck.name);

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

        view.btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert = new AlertDialog.Builder(context).create();
                View alertV = View.inflate(context, R.layout.dialog_additemdeck, null);

                final EditText edPharse = alertV.findViewById(R.id.edPhrase);
                final EditText edTranslate = alertV.findViewById(R.id.edTranslate);
                final BootstrapButton btnCancel = alertV.findViewById(R.id.btnCancel);
                final ProgressBar pbAddItemDeck = alertV.findViewById(R.id.pbAddItemDeck);
                final BootstrapButton btnAddItem = alertV.findViewById(R.id.btnAddItem);

                alert.setView(alertV);

                // Btn add

                btnAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pbAddItemDeck.setVisibility(View.VISIBLE);
                        Card card = new Card();
                        card.setFront(edPharse.getText().toString());
                        card.setBack(edTranslate.getText().toString());
                        card.setIdDeck(deckList.get(i).id);
                        card.setDay(2);

                        MainController mainController = new MainController(context);
                        mainController.addItemDeck(card, new MainControllerInterface() {
                            @Override
                            public void onCompleteSave(Boolean res) {
                                if(res){
                                    pbAddItemDeck.setVisibility(View.INVISIBLE);
                                    edPharse.setText("");
                                    edTranslate.setText("");
                                    Toast.makeText(context, "Adicionado!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onLoadQuantityDataToStudy(int quantity) {

                            }
                        });
                    }
                });

                //btn Cancel
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });
                alert.show();
            }
        });

        view.btnStudyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudyActivity.class);
                intent.putExtra("idDeck", deckList.get(i).id);
                context.startActivity(intent);
            }
        });

        MainController mainController = new MainController(context);
        mainController.getQuantityDataToStudy(deckList.get(i).id, new MainControllerInterface() {
            @Override
            public void onCompleteSave(Boolean res) {

            }

            @Override
            public void onLoadQuantityDataToStudy(int quantity) {
                String tvStudy = "Estudar hoje: " + quantity;
                view.tvStudyToday.setText(tvStudy);
            }
        });

        view.btnEditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditCardActivity.class);
                intent.putExtra("idDeck", deckList.get(i).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }
}
