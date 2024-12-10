package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application for fetching weather,
 * latitude/longitude, and food suggestions based on user input.
 */
public class ApiApp extends Application {
    Stage stage;
    Scene scene;
    VBox root;

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox(10); // Add spacing between elements
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        // Load banner image
        Image bannerImage = new Image("file:resources/readme-banner.png");
        ImageView banner = new ImageView(bannerImage);
        banner.setPreserveRatio(true);
        banner.setFitWidth(640);
        // UI Components
        Label notice = new Label("Weather & Food Suggestion Finder");
        Label cityLabel = new Label("Enter City:");
        TextField cityInput = new TextField();
        Button fetchButton = new Button("Fetch Weather and Food Suggestion");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        // Button Action
        fetchButton.setOnAction(e -> {
            String city = cityInput.getText().trim();
            if (city.isEmpty()) {
                resultArea.setText("Please enter a valid city name.");
                return;
            }
            try {
                // Fetch Coordinates
                GeocodingAPI.GeocodingResponse[] coordinates = GeocodingAPI.getCoordinates(city);
                String latitude = coordinates[0].latitude;
                String longitude = coordinates[0].longitude;
                // Fetch Weather Data
                WeatherAPI.WeatherResponse weatherResponse =
                        WeatherAPI.getWeather(latitude, longitude);
                double temperature = weatherResponse.currentWeather.temperature;
                String weatherCode = weatherResponse.currentWeather.weathercode;
                // Determine Food Suggestion Based on Weather Code
                String foodSuggestion = determineFoodSuggestion(weatherCode);
                // Display Results
                StringBuilder sb = new StringBuilder();
                sb.append("Weather in ").append(city).append(":\n");
                sb.append("Latitude: ").append(latitude).append("\n");
                sb.append("Longitude: ").append(longitude).append("\n");
                sb.append("Temperature: ").append(temperature).append("°C\n");
                sb.append("Weather Code: ").append(weatherCode).append("\n\n");
                sb.append("Suggested Food: ").append(foodSuggestion).append("\n");
                resultArea.setText(sb.toString());
            } catch (Exception ex) {
                resultArea.setText("Error fetching data: " + ex.getMessage());
            }
        });

        // Add all elements to the layout
        root.getChildren().addAll(banner, notice, cityLabel, cityInput, fetchButton, resultArea);

        // Setup scene
        scene = new Scene(root);
        stage.setTitle("Weather & Food Suggestion Finder");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    } // start

    /**
     * Determines the food suggestion based on the weather code.
     *
     * @param weatherCode the weather code from the Weather API
     * @return a food suggestion
     */
    private String determineFoodSuggestion(String weatherCode) {
        switch (weatherCode) {
        case "0": // Clear
        case "1": // Mostly Clear
            return "Salad";
        case "2": // Partly Cloudy
        case "3": // Overcast
            return "Comfort Food";
        case "45": // Fog
        case "48": // Freezing Fog
            return "Hot Soup";
        case "51": // Drizzle
        case "61": // Rain Showers
            return "Soup";
        default:
            return "Seasonal Dish";
        }
    }
} // ApiApp
