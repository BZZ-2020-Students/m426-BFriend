package bzz.groupa.bfriend.services;

import bzz.groupa.bfriend.enums.Hobby;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping("/api/hobby")
@Controller
public class HobbyService {
    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<?> getAllHobbies() {
        var hobbies = Hobby.values();

        var hobbiesList = new ArrayList<String>();
        for (Hobby hobby : hobbies) {
            hobbiesList.add(hobby.getNiceName());
        }

        return ResponseEntity.ok().body(hobbiesList);
    }
}
