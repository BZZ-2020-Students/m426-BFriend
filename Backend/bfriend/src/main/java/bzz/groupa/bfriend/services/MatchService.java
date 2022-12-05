package bzz.groupa.bfriend.services;

import bzz.groupa.bfriend.model.City;
import bzz.groupa.bfriend.model.User;
import bzz.groupa.bfriend.repositories.UserRepository;
import bzz.groupa.bfriend.repositories.UserRoleRepository;
import bzz.groupa.bfriend.security.jwt.JwtUtils;
import bzz.groupa.bfriend.security.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/match")
@Controller
public class MatchService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    @Value("${bfriend.app.X-RapidAPI-Host}")
    private String host;
    @Value("${bfriend.app.X-RapidAPI-Key}")
    private String key;

    public MatchService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepository roleRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping(path = "/get/{radius}", produces = "application/json")
    public ResponseEntity<?> getCandidate(HttpServletRequest request, @PathVariable("radius") int radius) {
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
}
