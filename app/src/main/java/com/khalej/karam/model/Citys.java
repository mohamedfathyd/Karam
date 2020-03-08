package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

public class Citys {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String ar_title;
    @SerializedName("en_title")
    String en_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAr_title() {
        return ar_title;
    }

    public void setAr_title(String ar_title) {
        this.ar_title = ar_title;
    }

    public String getEn_title() {
        return en_title;
    }

    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }
}
