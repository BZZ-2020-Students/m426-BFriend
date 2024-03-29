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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/infos")
    public ResponseEntity<?> checkIfLoggedIn(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        boolean valid = jwtUtils.validateJwtToken(jwt);
        if (valid) {
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            try {
                User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Error: User not found."));

                UserInfoResponse userInfoResponse = new UserInfoResponse(user.getId(),
                        user.getEmail(),
                        user.getFirstname(),
                        user.getLastName(),
                        user.getRoles());

                userInfoResponse.setProfilepicture(user.getProfilePicture());

                return ResponseEntity.ok()
                        .body(userInfoResponse);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid JWT"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong formatting!"));
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> optionalUser = userRepository.findById(userDetails.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setLastLogin(new Date());

            userRepository.save(user);

            return getResponseEntityWithCookie(userDetails, user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid E-Mail or Password"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong formatting!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("E-Mail is already taken!"));
        }

        // Create new user's account
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .firstname(signUpRequest.getFirstname())
                .lastName(signUpRequest.getLastname())
                .location(signUpRequest.getLocation())
                .gender(signUpRequest.getGender())
                .hobbies(signUpRequest.getHobbies())
                .age(signUpRequest.getAge())
                .profilePicture(signUpRequest.getProfilepicture())
                .build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
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
        user.setAccountCreated(new Date());
        user.setLastLogin(new Date());

        userRepository.save(user);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signUpRequest.getEmail(), signUpRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getResponseEntityWithCookie(userDetails, user);
    }

    private ResponseEntity<?> getResponseEntityWithCookie(UserDetailsImpl userDetails, User user) {
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userRoles, userDetails.getId(),
                        userDetails.getUsername(), user.getFirstname(), user.getLastName(), null));
    }
}
