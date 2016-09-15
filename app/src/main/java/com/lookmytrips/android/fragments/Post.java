package com.lookmytrips.android.fragments;

import com.lookmytrips.android.model.CardImages;
import com.lookmytrips.android.model.Cat;
import com.lookmytrips.android.model.Geo;
import com.lookmytrips.android.model.Places;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Win on 12.09.2016.
 */
public class Post implements Serializable{

    private String id;
    private String owner;
    private String likes;
    private String title;
    private String wasHere;
    private String shares;
    private int rating;
    private String geoHrCountry;
    private String geoHrCity;
    private String geoHrState;
    private String geoHrStreet;
    private String comments;
    private String cover;
    private String type;

    private String image;
    private String thumb;
    private String big;

    private String avatar;
    private String userName;

    private ArrayList<Places> placesList;
    private ArrayList<Geo> geoList;
    private ArrayList<Cat> catList;
    private ArrayList<CardImages> cardImages;

    public ArrayList<CardImages> getCardImages() {
        return cardImages;
    }

    public void setCardImages(ArrayList<CardImages> cardImages) {
        this.cardImages = cardImages;
    }

    public ArrayList<Places> getPlacesList() {
        return placesList;
    }

    public ArrayList<Geo> getGeoList() {
        return geoList;
    }

    public void setGeoList(ArrayList<Geo> geoList) {
        this.geoList = geoList;
    }

    public ArrayList<Cat> getCatList() {
        return catList;
    }

    public void setCatList(ArrayList<Cat> catList) {
        this.catList = catList;
    }

    public void setPlacesList(ArrayList<Places> placesList) {
        this.placesList = placesList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWasHere() {
        return wasHere;
    }

    public void setWasHere(String wasHere) {
        this.wasHere = wasHere;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGeoHrCountry() {
        return geoHrCountry;
    }

    public void setGeoHrCountry(String geoHrCountry) {
        this.geoHrCountry = geoHrCountry;
    }

    public String getGeoHrCity() {
        return geoHrCity;
    }

    public void setGeoHrCity(String geoHrCity) {
        this.geoHrCity = geoHrCity;
    }

    public String getGeoHrState() {
        return geoHrState;
    }

    public void setGeoHrState(String geoHrState) {
        this.geoHrState = geoHrState;
    }

    public String getGeoHrStreet() {
        return geoHrStreet;
    }

    public void setGeoHrStreet(String geoHrStreet) {
        this.geoHrStreet = geoHrStreet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }
}
