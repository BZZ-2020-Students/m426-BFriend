package bzz.groupa.bfriend.security.services;

import bzz.groupa.bfriend.util.GlobalVars;
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
}
