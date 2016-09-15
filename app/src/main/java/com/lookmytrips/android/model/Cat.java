package com.lookmytrips.android.model;

import java.io.Serializable;

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
