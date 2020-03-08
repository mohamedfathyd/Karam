package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CardRealm extends RealmObject {
    @SerializedName("product_id")
    int id;
    @SerializedName("allid")
    int allid;
    @SerializedName("name")
    String name;
    @SerializedName("price")
    double price;
    @SerializedName("amount")
    int num;
    @SerializedName("image")
    String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllid() {
        return allid;
    }

    public void setAllid(int allid) {
        this.allid = allid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
