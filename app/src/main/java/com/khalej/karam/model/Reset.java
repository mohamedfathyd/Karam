package com.khalej.karam.model;

import com.google.gson.annotations.SerializedName;

public class Reset {
    @SerializedName("can")
    int can;

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }
}
