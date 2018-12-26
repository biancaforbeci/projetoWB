package com.example.biaeweverton.projetowb.files.Models;

import java.util.ArrayList;

public interface MainControllerInterface {
    public void onCompleteSave(Boolean res);
    public void onLoadQuantityDataToStudy(int quantity);
    public void onLoadingDeck(ArrayList<Deck> listDeck);
}
