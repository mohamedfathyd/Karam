package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

public class contact_SubScribe {

    @SerializedName("category_id")
    int id;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("en_title")
    String en_title;
    @SerializedName("description")
    String description;
    @SerializedName("weight")
    String weight;
    @SerializedName("unit")
    String unit;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("main_image")
    String main_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
