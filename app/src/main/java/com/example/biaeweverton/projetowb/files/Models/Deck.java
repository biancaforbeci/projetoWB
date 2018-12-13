package com.example.biaeweverton.projetowb.files.Models;


import java.io.Serializable;

public class Deck implements Serializable {
    public String name;
    public String idUser;
    public String id;
    public Deck(){}
    public Deck(String name, String idUser){
        this.name = name;
        this.idUser = idUser;
    }
}
