package com.lookmytrips.android.pojo;

import java.io.Serializable;

public class Places implements Serializable {

    private String type;
    private String name;

    public Places (String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
