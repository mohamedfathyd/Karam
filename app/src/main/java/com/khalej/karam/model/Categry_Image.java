package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

public class Categry_Image {
    @SerializedName("image")
    String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
