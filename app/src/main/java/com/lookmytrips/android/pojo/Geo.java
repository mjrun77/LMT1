package com.lookmytrips.android.pojo;

import java.io.Serializable;

/**
 * Created by Win on 14.09.2016.
 */

public class Geo implements Serializable {

    private String lat;
    private String lon;

    public Geo(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
