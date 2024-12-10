package cs1302.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

/**
 * Weather API for fetching weather details.
 */
public class WeatherAPI {

    private static final String WEATHER_API = "https://api.open-meteo.com/v1/forecast";

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
