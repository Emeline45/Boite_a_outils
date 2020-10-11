package com.example.boiteoutils;

public class Annuaire {

    private final String name;
    private final String number;

    public Annuaire (String nom, String numero){
        this.name = nom ;
        this.number = numero ;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }
}
