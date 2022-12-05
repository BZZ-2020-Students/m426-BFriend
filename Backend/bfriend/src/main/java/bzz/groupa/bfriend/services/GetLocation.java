package bzz.groupa.bfriend.services;

import bzz.groupa.bfriend.model.City;
import bzz.groupa.bfriend.model.User;
import bzz.groupa.bfriend.repositories.UserRepository;
import bzz.groupa.bfriend.repositories.UserRoleRepository;
import bzz.groupa.bfriend.security.jwt.JwtUtils;
import bzz.groupa.bfriend.security.payload.response.MessageResponse;
import bzz.groupa.bfriend.util.GlobalVars;
import bzz.groupa.bfriend.util.annotation.WikiDataIdValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/location")
@Controller
public class GetLocation {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    @Value("${bfriend.app.X-RapidAPI-Host}")
    private String host;
    @Value("${bfriend.app.X-RapidAPI-Key}")
    private String key;

    public GetLocation(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepository roleRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public static List<City> getNearbyCities(int radius, String city, String key, String host) throws IOException {
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

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        List<City> cities = new ArrayList<>();

        JsonNode data = new ObjectMapper().readTree(responseString).get("data");
        for (JsonNode cityNode : data) {
            City city1 = new City();
            city1.setWikiDataId(cityNode.get("wikiDataId").asText());
            city1.setName(cityNode.get("name").asText());

            cities.add(city1);
        }

        return cities;
    }

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
        try {
            List<City> cities = getNearbyCities(radius, city, key, host);
            return ResponseEntity.ok().body(cities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/nearme/{radius}", produces = "application/json")
    public ResponseEntity<?> findNearbyCities(HttpServletRequest request, @PathVariable int radius) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        boolean valid = jwtUtils.validateJwtToken(jwt);
        if (valid) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            try {
                User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Error: User not found."));
                List<City> closeCities = GetLocation.getNearbyCities(radius, user.getLocation().split(";")[0], key, host);
                return ResponseEntity.ok(closeCities);
            } catch (RuntimeException | IOException e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid JWT"));
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
