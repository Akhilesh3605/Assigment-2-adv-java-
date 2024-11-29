package com.example.localeventfinder;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EventDetailsController {

    @FXML
    private Label eventDetailsLabel;

    public void setEventDetails(String eventDetails) {
        // Display the event details
        eventDetailsLabel.setText(eventDetails);
    }
}
