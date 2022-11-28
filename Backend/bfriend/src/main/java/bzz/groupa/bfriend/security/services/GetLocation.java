package bzz.groupa.bfriend.security.services;

import bzz.groupa.bfriend.util.GlobalVars;
import bzz.groupa.bfriend.util.annotation.WikiDataIdValidator;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@RequestMapping("/api/location")
@Controller
public class GetLocation {
    @Value("${bfriend.app.X-RapidAPI-Host}")
    private String host;

    @Value("${bfriend.app.X-RapidAPI-Key}")
    private String key;

    @GetMapping(path = "/search/{city}", produces = "application/json")
    public ResponseEntity<?> findCity(@PathVariable String city) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://wft-geo-db.p.rapidapi.com/v1/geo/cities?countryIds=" + GlobalVars.COUNTRY_IDS + "&namePrefix=" + city)
                .get()
                .addHeader("X-RapidAPI-Key", key)
                .addHeader("X-RapidAPI-Host", host)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ResponseEntity.ok().body(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/near/{city}/{radius}", produces = "application/json")
    public ResponseEntity<?> findNearbyCities(@PathVariable String city, @PathVariable int radius) {
        if (radius > 100) {
            radius = 100;
        }

        if (radius < 1) {
            radius = 1;
        }

        if (!WikiDataIdValidator.isValid(city)) {
            throw new RuntimeException("Invalid WikiDataID");
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://wft-geo-db.p.rapidapi.com/v1/geo/cities/" + city + "/nearbyCities?radius=" + radius + "&distanceUnit=KM&countryIds=" + GlobalVars.COUNTRY_IDS)
                .get()
                .addHeader("X-RapidAPI-Key", key)
                .addHeader("X-RapidAPI-Host", host)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ResponseEntity.ok().body(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/distance/{city1}/{city2}", produces = "application/json")
    public ResponseEntity<?> findDistanceBetweenCities(@PathVariable String city1, @PathVariable String city2) {
        if (!WikiDataIdValidator.isValid(city1) || !WikiDataIdValidator.isValid(city2)) {
            throw new RuntimeException("Invalid WikiDataID");
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://wft-geo-db.p.rapidapi.com/v1/geo/cities/" + city1 + "/distance?distanceUnit=KM&toCityId=" + city2)
                .get()
                .addHeader("X-RapidAPI-Key", key)
                .addHeader("X-RapidAPI-Host", host)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return ResponseEntity.ok().body(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
