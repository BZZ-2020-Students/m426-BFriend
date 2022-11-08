package bzz.groupa.bfriend.security.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

import static bzz.groupa.bfriend.util.GlobalVars.*;

public class SignupRequest {
    @NotBlank
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH)
    private String username;

    private Set<String> role;

    @NotBlank
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
