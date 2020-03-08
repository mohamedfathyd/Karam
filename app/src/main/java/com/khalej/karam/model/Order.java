package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("des")
    String details;
    @SerializedName("created_at")
    String date;
    @SerializedName("total")
    String Price;
    @SerializedName("amount")
    String amount;
    @SerializedName("latitude")
    double latfrom;
    @SerializedName("longitude")
    double latTo;

    @SerializedName("address")
    String address;
    @SerializedName("status")
    int status;
    @SerializedName("representative_name")
    String representative_name;
    @SerializedName("representative_phone")
    String representative_phone;
    @SerializedName("representative_image")
    String representative_image;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public double getLatfrom() {
        return latfrom;
    }

    public void setLatfrom(double latfrom) {
        this.latfrom = latfrom;
    }

    public double getLatTo() {
        return latTo;
    }

    public void setLatTo(double latTo) {
        this.latTo = latTo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRepresentative_name() {
        return representative_name;
    }

    public void setRepresentative_name(String representative_name) {
        this.representative_name = representative_name;
    }

    public String getRepresentative_phone() {
        return representative_phone;
    }

    public void setRepresentative_phone(String representative_phone) {
        this.representative_phone = representative_phone;
    }

    public String getRepresentative_image() {
        return representative_image;
    }

    public void setRepresentative_image(String representative_image) {
        this.representative_image = representative_image;
    }
}


