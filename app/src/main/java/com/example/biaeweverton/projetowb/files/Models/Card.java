package com.example.biaeweverton.projetowb.files.Models;

public class Card {
    private String id;
    private String idDeck;
    private String front;
    private String back;
    private int day;
    private boolean repeat;

    public String getIdDeck() {
        return idDeck;
    }

    public void setIdDeck(String idDeck) {
        this.idDeck = idDeck;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setRepeat(boolean b) {
        this.repeat = b;
    }

    public boolean getRepeat(){
        return this.repeat;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
