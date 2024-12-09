package cs1302.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Class for interacting with the Geocoding API to fetch coordinates.
 */
public class GeocodingAPI {

    private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";

    /**
     * Fetches coordinates for a given city.
     *
     * @param city the city to fetch coordinates for
     * @return an array of {@code GeocodingResponse} objects containing latitude and longitude
     * @throws Exception if the API call fails or no data is returned
     */
    public static GeocodingResponse[] getCoordinates(String city) throws Exception {
        // Properly encode the city name
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format("%s?q=%s&format=json&limit=1", BASE_URL, encodedCity);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "JavaGeocodingClient") // Required by the API
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the JSON response
        Gson gson = new Gson();
        GeocodingResponse[] results = gson.fromJson(response.body(), GeocodingResponse[].class);

        // Handle empty results
        if (results == null || results.length == 0) {
            throw new Exception("City not found or no data returned from Geocoding API.");
        }

        return results;
    }

    /**
     * Represents a response from the Geocoding API.
     */
    public static class GeocodingResponse {
        @SerializedName("lat")
        public String latitude;

        @SerializedName("lon")
        public String longitude;

        @SerializedName("display_name")
        public String displayName;
    }
}
