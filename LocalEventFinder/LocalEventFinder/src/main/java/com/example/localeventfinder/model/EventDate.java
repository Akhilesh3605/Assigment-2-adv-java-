package com.example.localeventfinder.model;

import com.google.gson.annotations.SerializedName;

public class EventDate {

    @SerializedName("start")
    private StartDate startDate;

    public String getLocalDate() {
        return startDate != null ? startDate.getLocalDate() : "No date available";
    }

    // Nested class to represent the "start" object from the API
    class StartDate {

        @SerializedName("localDate")
        private String localDate;

        public String getLocalDate() {
            return localDate;
        }
    }
}
