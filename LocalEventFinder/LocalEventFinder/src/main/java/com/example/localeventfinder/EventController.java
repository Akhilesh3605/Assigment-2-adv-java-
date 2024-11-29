package com.example.localeventfinder;

import com.example.localeventfinder.model.Event;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class EventController {

    // Your Ticketmaster API key
    private static final String API_KEY = "WAYWlbNMkmkDGXHRxpLanNoNPP8vdy1J";  // Replace with your actual API Key
    private static final String API_URL = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=" + API_KEY;

    // ListView to display event details
    @FXML
    private ListView<String> eventListView;

    // Fetch events from the Ticketmaster API using Java 11's built-in HttpClient
    public List<Event> fetchEvents() {
        List<Event> events = new ArrayList<>();
        try {
            // Use the base URL without any specific location
            String modifiedAPIURL = API_URL;  // You can add additional parameters if needed

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(modifiedAPIURL))  // Use the modified API URL without location parameter
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response status code is 200 (OK)
            if (response.statusCode() == 200) {
                // Print the raw response for debugging
                System.out.println("API Response: " + response.body()); // Log the response body

                // Parse the JSON response into Event objects using Gson
                Gson gson = new Gson();
                TicketmasterResponse responseObj = gson.fromJson(response.body(), TicketmasterResponse.class);

                // Check if the response contains events
                if (responseObj != null && responseObj._embedded != null && responseObj._embedded.events != null) {
                    events.addAll(responseObj._embedded.events);
                    // Debugging: Log events data
                    for (Event event : events) {
                        System.out.println("Event Name: " + event.getName());
                        System.out.println("Event Location: " + event.getLocation());
                        System.out.println("Event Date: " + event.getDate());
                    }
                } else {
                    System.out.println("No events found in the API response.");
                }
            } else {
                System.err.println("Error: Received response code " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace for debugging
        }
        return events;
    }

    // Method to be triggered by the "Fetch Events" button
    @FXML
    public void onFetchEventsButtonClick() {
        List<Event> events = fetchEvents();  // Fetch events from the API

        // Clear the ListView before adding new events
        eventListView.getItems().clear();

        // Add event names, locations, and dates to the ListView
        for (Event event : events) {
            String eventDetails = String.format("%s - %s - %s", event.getName(), event.getLocation(), event.getDate());
            eventListView.getItems().add(eventDetails);
        }

        // Add click listener to each event in the ListView
        eventListView.setOnMouseClicked(event -> {
            // Get the selected event index
            int selectedIndex = eventListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Event selectedEvent = events.get(selectedIndex);

                // Show the event details in an alert (could be a new page)
                showEventDetails(selectedEvent);
            }
        });
    }

    // Method to display event details
    private void showEventDetails(Event selectedEvent) {
        // Create a new alert dialog to show the event details
        Alert eventAlert = new Alert(AlertType.INFORMATION);
        eventAlert.setTitle("Event Details");
        eventAlert.setHeaderText("Details of: " + selectedEvent.getName());

        String eventDetails = String.format("Description: %s\nLocation: %s\nDate: %s",
                selectedEvent.getDescription(),
                selectedEvent.getLocation(),
                selectedEvent.getDate());

        eventAlert.setContentText(eventDetails);

        // Show the alert with the event details
        eventAlert.showAndWait();
    }

    // Internal class to map the Ticketmaster API response structure
    private static class TicketmasterResponse {
        private Embedded _embedded;

        public Embedded get_embedded() {
            return _embedded;
        }

        private static class Embedded {
            private List<Event> events;

            public List<Event> getEvents() {
                return events;
            }
        }
    }
}
