package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Add_order {
    @SerializedName("user_id")
     int id;
    @SerializedName("address")
    String address;
    @SerializedName("name")
    String name;
    @SerializedName("latitude")
    Float lat;
    @SerializedName("longitude")
    Float log;
    @SerializedName("orderDetalis")
    List<CardRealm> orderDetalis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLog() {
        return log;
    }

    public void setLog(Float log) {
        this.log = log;
    }

    public List<CardRealm> getOrderDetalis() {
        return orderDetalis;
    }

    public void setOrderDetalis(List<CardRealm> orderDetalis) {
        this.orderDetalis = orderDetalis;
    }
}
