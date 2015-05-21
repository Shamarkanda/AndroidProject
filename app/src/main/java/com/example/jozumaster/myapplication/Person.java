package com.example.jozumaster.myapplication;

import java.io.Serializable;

/**
 * Created by JozuMaster on 17/05/2015.
 */
public class Person implements Serializable{
    private int idPersonalData;
    private String email;
    private String username;
    private String password;
    private int idProvince;
    private String birthDate;
    private String genre;

    public Person(){
        this.idPersonalData = 0;
        this.email = "";
        this.username = "";
        this.password = "";
        this.idProvince = 0;
        this.birthDate = "";
        this.genre = "";
    }

    public Person(int idPersonalData, String email, String username, String password, int idProvince, String birthDate, String genre){
        this.idPersonalData = idPersonalData;
        this.email = email;
        this.username = username;
        this.password = password;
        this.idProvince = idProvince;
        this.birthDate = birthDate;
        this.genre = genre;
    }

    public int getIdPersonalData(){ return idPersonalData; }

    public void setIdPersonalData(int idPersonalData){ this.idPersonalData = idPersonalData; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdProvince() {
        return idProvince;
    }

    public void setIdProvince(int idProvince) {
        this.idProvince = idProvince;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
