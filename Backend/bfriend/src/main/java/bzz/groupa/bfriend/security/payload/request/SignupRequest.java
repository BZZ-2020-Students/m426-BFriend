package bzz.groupa.bfriend.security.payload.request;

import bzz.groupa.bfriend.enums.Gender;
import bzz.groupa.bfriend.enums.Hobby;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Set;

import static bzz.groupa.bfriend.util.GlobalVars.*;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    @Size(max = MAX_EMAIL_LENGTH)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    private String password;

    @NotBlank
    @Size(max = MAX_NAME_LENGTH)
    private String firstname;

    @NotBlank
    @Size(max = MAX_NAME_LENGTH)
    private String lastname;

    @NotNull
    private Set<Hobby> hobbies;

    @NotBlank
    @Size(max = MAX_LOCATION_LENGTH)
    private String location;

    @NotBlank
    private String profilepicture;

    @NotNull
    private Gender gender;

    @NotNull
    @Max(MAX_AGE)
    private int age;



}
