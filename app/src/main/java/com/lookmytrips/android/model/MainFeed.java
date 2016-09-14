package com.lookmytrips.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFeed {

    public Items items;
    public void setItems(Items items) {
        this.items = items;
    }

    public Items getItems() {
        return items;
    }

    // length...
    public class Items {
        @SerializedName("feed")
        private ArrayList<Feed> feeds;

        public void setFeeds(ArrayList<Feed> feeds) {
            this.feeds = feeds;
        }

        public ArrayList<Feed> getFeeds() {
            return feeds;
        }

        @SerializedName("users")
        public Object users;

//        public Users getUsers() {
//            return users;
//        }
//
//        public void setUsers(Users users) {
//            this.users = users;
//        }


    }


    public class Feed implements Serializable{

        @SerializedName("GeoHr")
        GeoHr geoHr;
//        @SerializedName("Images")
//        public HashMap<String, String> imageeees;


//        @SerializedName("Places")
//        private ArrayList<Places> places;

        @SerializedName("_id")
        private String _id;

        @SerializedName("thumb")
        private String thumb;

        @SerializedName("Cover")
        private Object cover;

        @SerializedName("Images")
        public Object images;

        @SerializedName("Geo")
        public Object geo;

//        public ArrayList<Places> getPlaces() {
//            return places;
//        }
//
//        public void setPlaces(ArrayList<Places> places) {
//            this.places = places;
//        }

        public Object getCover() {
            return cover;
        }

        public void setCover(Object cover) {
            this.cover = cover;
        }

//        public LinkedTreeMap<String, String> getImages() {
//            return images;
//        }
//
//        public void setImages(LinkedTreeMap<String, String> images) {
//            this.images = images;
//        }

        @SerializedName("Title")
        private String title;

        @SerializedName("Owner")
        private String owner;

        @SerializedName("Likes")
        private String likes;

        @SerializedName("Published")
        private String published;

        @SerializedName("WasHere")
        private String wasHere;

        @SerializedName("Shares")
        private String shares;

        @SerializedName("Rating")
        private String rating;

        @SerializedName("Comments")
        private String comments;

        @SerializedName("ModerFlag")
        private String moderFlag;

        @SerializedName("Type")
        private String type;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public GeoHr getGeoHr() {
            return geoHr;
        }

        public void setGeoHr(GeoHr geoHr) {
            this.geoHr = geoHr;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getPublished() {
            return published;
        }

        public void setPublished(String published) {
            this.published = published;
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

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getModerFlag() {
            return moderFlag;
        }

        public void setModerFlag(String moderFlag) {
            this.moderFlag = moderFlag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setValue_id(String value_id) {
            this._id = value_id;
        }

        public String getValue_id() {
            return _id;
        }

        public class Images implements Serializable{

            @SerializedName("thumb")
            private String thumbb;

            @SerializedName("big")
            private String bigg;

            public String getThumbb() {
                return thumbb;
            }

            public void setThumbb(String thumbb) {
                this.thumbb = thumbb;
            }

            public String getBigg() {
                return bigg;
            }

            public void setBigg(String bigg) {
                this.bigg = bigg;
            }
        }

    }




    public class Users implements Serializable {
        @SerializedName("Name")
        private String userName;

        @SerializedName("Avatar")
        private String userAvatar;

        @SerializedName("id")
        private String userId;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

}