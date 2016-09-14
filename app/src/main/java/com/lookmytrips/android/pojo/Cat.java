package com.lookmytrips.android.pojo;

import java.io.Serializable;

/**
 * Created by Win on 14.09.2016.
 */

public class Cat implements Serializable {

    private String catTitle;

    public Cat(String catTitle) {
        this.catTitle = catTitle;
    }

    public String getCatTitle() {
        return catTitle;
    }

    public void setCatTitle(String catTitle) {
        this.catTitle = catTitle;
    }
}
