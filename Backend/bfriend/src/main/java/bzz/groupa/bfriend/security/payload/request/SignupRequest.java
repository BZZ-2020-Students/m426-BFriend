package bzz.groupa.bfriend.security.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    private String Lastname;

    @NotBlank
    private Set<String> hobbys;

    @NotBlank
    @Size(max = MAX_LOCATION_LENGTH)
    private String location;

    @NotBlank
    private String profilepicture;

    @NotBlank
    private String gender;

    @NotBlank
    @Size(max = MAX_AGE)
    private int age;



}
