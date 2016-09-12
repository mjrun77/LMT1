package com.lookmytrips.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 12.09.2016.
 */

public class FeedUsers implements Serializable{

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
