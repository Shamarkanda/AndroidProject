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
    private String province;
    private String birthDate;
    private String genre;
    private int friendsNumber;
    private int videogamesNumber;
    private boolean followed;

    public Person(){
        this.idPersonalData = 0;
        this.email = "";
        this.username = "";
        this.password = "A!Sg23P";
        this.province = "";
        this.birthDate = "";
        this.genre = "";
        this.friendsNumber = 0;
        this.videogamesNumber = 0;
        this.followed = false;
    }

    public Person(int idPersonalData, String email, String username, String password, String province, String birthDate, String genre, int friendsNumber, int videogamesNumber, boolean followed){
        this.idPersonalData = idPersonalData;
        this.email = email;
        this.username = username;
        this.password = password;
        this.province = province;
        this.birthDate = birthDate;
        this.genre = genre;
        this.friendsNumber = friendsNumber;
        this.videogamesNumber = videogamesNumber;
        this.followed = followed;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public int getFriendsNumber() {
        return friendsNumber;
    }

    public void setFriendsNumber(int friendsNumber) {
        this.friendsNumber = friendsNumber;
    }

    public int getVideogamesNumber() {
        return videogamesNumber;
    }

    public void setVideogamesNumber(int videogamesNumber) {
        this.videogamesNumber = videogamesNumber;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
