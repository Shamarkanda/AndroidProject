package com.example.jozumaster.myapplication;

/**
 * Created by JozuMaster on 27/05/2015.
 */
public class Videogame {
    private int idVideogame;
    private String genre;
    private String videogameName;
    private int players;
    private int pegi;
    private String releaseDate;
    private String videogameImage;
    private boolean followed;

    public Videogame(){
        this.idVideogame = 0;
        this.genre = "";
        this.videogameName = "";
        this.players = 0;
        this.pegi = 0;
        this.releaseDate = "";
        this.videogameImage = "";
        this.followed = false;
    }

    public Videogame(int idVideogame, String genre, String videogameName, int players, int pegi, String releaseDate, String videogameImage, boolean followed){
        this.idVideogame = idVideogame;
        this.genre = genre;
        this.videogameName = videogameName;
        this.players = players;
        this.pegi = pegi;
        this.releaseDate = releaseDate;
        this.videogameImage = videogameImage;
        this.followed = followed;
    }

    public int getIdVideogame() {
        return idVideogame;
    }

    public void setIdVideogame(int idVideogame) {
        this.idVideogame = idVideogame;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getVideogameName() {
        return videogameName;
    }

    public void setVideogameName(String videogameName) {
        this.videogameName = videogameName;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getPegi() {
        return pegi;
    }

    public void setPegi(int pegi) {
        this.pegi = pegi;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVideogameImage() {
        return videogameImage;
    }

    public void setVideogameImage(String videogameImage) {
        this.videogameImage = videogameImage;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
