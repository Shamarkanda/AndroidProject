package com.example.jozumaster.myapplication;

/**
 * Created by JozuMaster on 19/05/2015.
 */
public class Categorie {
    private int idCategorie;
    private String nameCategorie;
    private int imageCategorie;

    public Categorie(){
        this.idCategorie = 0;
        this.nameCategorie = "";
        this.imageCategorie = 0;
    }

    public Categorie(int idCategorie, String nameCategorie, int imageCategorie){
        this.idCategorie = idCategorie;
        this.nameCategorie = nameCategorie;
        this.imageCategorie = imageCategorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNameCategorie() {
        return nameCategorie;
    }

    public void setNameCategorie(String nameCategorie) {
        this.nameCategorie = nameCategorie;
    }

    public int getImageCategorie() {
        return imageCategorie;
    }

    public void setImageCategorie(int imageCategorie) {
        this.imageCategorie = imageCategorie;
    }
}
