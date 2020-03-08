package com.khalej.karam.model;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class contact_home{
    @SerializedName("category_id")
    int id;
    @SerializedName("ar_title")
    String ar_title;
    @SerializedName("en_title")
    String en_title;
    @SerializedName("main_image")
    String image;
    @SerializedName("price")
    Float price;
    @SerializedName("description")
    String details;
    @SerializedName("product_images")
    List<Categry_Image> images;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Categry_Image> getImages() {
        return images;
    }

    public void setImages(List<Categry_Image> images) {
        this.images = images;
    }
}
