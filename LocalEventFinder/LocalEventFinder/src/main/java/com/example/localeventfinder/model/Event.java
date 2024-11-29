package com.example.localeventfinder.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Event {

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("dates")
    private EventDate dates; // Note: Changed from 'date' to 'dates' for clarity

    @SerializedName("_embedded") // Assuming the venue is nested within the "_embedded" object
    private Embedded embedded;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalDate() {
        return (dates != null && dates.getStart() != null) ? dates.getStart().getLocalDate() : "Date not available";
    }

    public String getLocation() {
        if (embedded != null && embedded.getVenues() != null && !embedded.getVenues().isEmpty()) {
            Venue venue = embedded.getVenues().get(0); // Get the first venue
            return venue.getName() + ", " + (venue.getCity() != null ? venue.getCity().getName() : "City not available");
        }
        return "Location not available"; // Ensure this return statement is always reached
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + getLocation() + '\'' +
                ", date='" + getLocalDate() + '\'' +
                '}';
    }

    // Corrected getDate() method with return statement
    public String getDate() {
        return (dates != null && dates.getStart() != null) ? dates.getStart().getLocalDate() : "Date not available";
    }

    // Inner classes for EventDate and Embedded
    public static class EventDate {
        @SerializedName("start")
        private Start start;

        public Start getStart() {
            return start;
        }

        public static class Start {
            @SerializedName("localDate")
            private String localDate;

            public String getLocalDate() {
                return localDate;
            }
        }
    }

    public static class Embedded {
        @SerializedName("venues")
        private List<Venue> venues;

        public List<Venue> getVenues() {
            return venues;
        }
    }

    // Venue class and nested City class
    public static class Venue {
        @SerializedName("name")
        private String name;

        @SerializedName("city")
        private City city;

        public String getName() {
            return name;
        }

        public City getCity() {
            return city;
        }

        public static class City {
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}
