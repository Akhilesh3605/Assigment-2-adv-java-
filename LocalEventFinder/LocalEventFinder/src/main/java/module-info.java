module com.example.localeventfinder {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;

    // Gson for JSON parsing
    requires com.google.gson;

    // HTTP Client module (Java 11+)
    requires java.net.http;

    // Open the necessary packages for reflection
    opens com.example.localeventfinder to javafx.fxml, com.google.gson; // Allow reflection for JavaFX and Gson
    opens com.example.localeventfinder.model to com.google.gson; // Allow reflection for Gson

    // Export the main package for other modules to access
    exports com.example.localeventfinder;
    exports com.example.localeventfinder.model; // This will allow the model package to be accessed
}