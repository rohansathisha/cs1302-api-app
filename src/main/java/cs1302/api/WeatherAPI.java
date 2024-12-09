package cs1302.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Weather API for fetching weather details.
 */
public class WeatherAPI {

    private static final String NOMINATIM_API = "https://nominatim.openstreetmap.org/search?q=";
    private static final String WEATHER_API = "https://api.open-meteo.com/v1/forecast";

    /**
     * Fetches the coordinates for a given city using the Nominatim API.
     *
     * @param city the name of the city to fetch coordinates for
     * @return a {@code CoordinatesResponse} object containing latitude and longitude
     * @throws Exception if no coordinates are found or the API call fails
     */
    public static CoordinatesResponse getCoordinates(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = NOMINATIM_API + encodedCity + "&format=json&limit=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        CoordinatesResponse[] coordinates =
                gson.fromJson(response.body(), CoordinatesResponse[].class);

        if (coordinates.length == 0) {
            throw new Exception("No coordinates found for the specified city.");
        }

        return coordinates[0];
    }

    /**
     * Fetches the weather details for the given coordinates using the Open-Meteo API.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @return a {@code WeatherResponse} object containing the weather details
     * @throws Exception if the API call fails or the data cannot be parsed
     */
    public static WeatherResponse getWeather(String latitude, String longitude) throws Exception {
        String url = WEATHER_API +
                "?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        return gson.fromJson(response.body(), WeatherResponse.class);
    }

    /**
     * Represents the response from the Nominatim API for fetching coordinates.
     */
    public static class CoordinatesResponse {
        @SerializedName("lat")
        public String latitude;

        @SerializedName("lon")
        public String longitude;
    }

    /**
     * Represents the response from the Open-Meteo API for fetching weather details.
     */
    public static class WeatherResponse {
        @SerializedName("current_weather")
        public CurrentWeather currentWeather;

        /**
         * Represents the current weather details.
         */
        public static class CurrentWeather {
            public double temperature;
            public String weathercode;
        }
    }
}
