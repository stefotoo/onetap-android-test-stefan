package com.receiptbank.android.testapp.rest.model;

import com.google.gson.annotations.SerializedName;

public class Weather {

    // variables
    @SerializedName("main")
    private String title;
    @SerializedName("description")
    private String description;

    // constructor
    public Weather() {
        // default
    }

    // methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
