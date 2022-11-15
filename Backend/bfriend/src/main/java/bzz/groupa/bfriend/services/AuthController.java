package bzz.groupa.bfriend.services;

import bzz.groupa.bfriend.enums.EnumUserRole;
import bzz.groupa.bfriend.model.User;
import bzz.groupa.bfriend.model.UserRole;
import bzz.groupa.bfriend.repositories.UserRepository;
import bzz.groupa.bfriend.repositories.UserRoleRepository;
import bzz.groupa.bfriend.security.jwt.JwtUtils;
import bzz.groupa.bfriend.security.payload.request.LoginRequest;
import bzz.groupa.bfriend.security.payload.request.SignupRequest;
import bzz.groupa.bfriend.security.payload.response.MessageResponse;
import bzz.groupa.bfriend.security.payload.response.UserInfoResponse;
import bzz.groupa.bfriend.security.services.UserDetailsImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api/auth")
@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You have been logged out successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong formatting!"));
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastLogin(new Date());

            userRepository.save(user);

            return getResponseEntityWithCookie(userDetails);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong formatting!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Username is already taken!"));
        }
        // Create new user's account
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> roles = new HashSet<>();

        if (strRoles == null) {
            UserRole userRole = roleRepository.findByName(EnumUserRole.PASSIVE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                var myRole = EnumUserRole.getRoleByString(role);
                if (myRole != null) {
                    UserRole userRole = roleRepository.findByName(myRole)
                            .orElseThrow(() -> new RuntimeException("Error: Role '" + role + "' is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        userRepository.save(user);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getResponseEntityWithCookie(userDetails);
    }

    private ResponseEntity<?> getResponseEntityWithCookie(UserDetailsImpl userDetails) {
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userRoles));
    }
}
