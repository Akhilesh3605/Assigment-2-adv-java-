package com.example.localeventfinder.model;

import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName("name")
    private String name;

    // Getter method to access the venue name
    public String getName() {
        return name;
    }
}
