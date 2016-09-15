package com.lookmytrips.android.model;

import java.io.Serializable;

public class CardImages implements Serializable {

    private String title;
    private String mediaId;
    private String geo;
    private String big;
    private String url;

    public CardImages(String title, String mediaId, String geo, String big, String url) {
        this.title = title;
        this.mediaId = mediaId;
        this.geo = geo;
        this.big = big;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
