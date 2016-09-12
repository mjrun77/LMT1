package com.lookmytrips.android.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Win on 12.09.2016.
 */
public class ImageThumb implements Serializable {

    @SerializedName("thumb")
    private String thumb;
    @SerializedName("big")
    private String big;
    @SerializedName("image")
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
